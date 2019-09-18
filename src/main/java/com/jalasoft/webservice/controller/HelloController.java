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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
    public String SayHello(){
        return "Hello";
    }

    @RequestMapping("/convert")
    @GetMapping
    public String SayHello(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "lastName", defaultValue = "") String lastName) {
        StringBuilder firstResponse = new StringBuilder();
        firstResponse.append("Hi ").append(name).append(" ").append(lastName);
        return firstResponse.toString();
    }
}
