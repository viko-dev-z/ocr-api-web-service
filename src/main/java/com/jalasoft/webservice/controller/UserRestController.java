/*
 *
 *  Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with Jalasoft.
 * /
 */

package com.jalasoft.webservice.controller;

import com.jalasoft.webservice.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserRestController extends ProcessAbstractRestController {

    public UserRestController(){

    }

    @PostMapping ("/login")
    public String validate(@RequestBody User user) {
        User userObject = dbm.getUser(user.getUser(), user.getPassword());
        if(userObject == null){
            return "Error 401";
        }

        String key = "dev-fun2";
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, key.getBytes())
                                    .setSubject(userObject.getUser())
                                    .claim("role", userObject.getRole())
                                    .claim("email", userObject.getEmail())
                                    .compact();
        Cache.getInstance().addToken(token);
        return token;
    }
}
