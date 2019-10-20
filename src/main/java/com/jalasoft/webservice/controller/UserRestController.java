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

import com.jalasoft.webservice.common.ResponseBuilder;
import com.jalasoft.webservice.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserRestController extends ProcessAbstractRestController {

    public UserRestController(){

    }

    @PostMapping ("/login")
    public ResponseEntity validate(@RequestBody User user) {
        User userObject = dbm.getUser(user.getUser(), user.getPassword());
        if(userObject == null){
            ResponseErrorMessage responseError = new ResponseErrorMessage();
            responseError.setMessage("Invalid User");
            return ResponseBuilder.getResponse(HttpStatus.UNAUTHORIZED,responseError);
        }

        String key = "dev-fun2";
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, key.getBytes())
                                    .setSubject(userObject.getUser())
                                    .claim("role", userObject.getRole())
                                    .claim("email", userObject.getEmail())
                                    .compact();
        Cache.getInstance().addToken(token);
        ResponseOkMessage responseOkMessage = new ResponseOkMessage();
        responseOkMessage.setCustomMessage("token", token);
        return ResponseBuilder.getResponse(HttpStatus.OK, responseOkMessage);
    }
}
