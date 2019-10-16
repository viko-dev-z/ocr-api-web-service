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

import java.util.ArrayList;
import java.util.List;

public class Cache {
    private static Cache cache;
    private static List<String> tokens;

    private Cache() {
        tokens = new ArrayList<String>();
    }

    public static Cache getInstance(){
        if (cache == null){
            cache = new Cache();
        }
        return cache;
    }

    public static void addToken(String token){
        tokens.add(token);
    }
}
