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

import org.json.JSONObject;

/**
 * Abstract class that represents Response object
 *
 * @author  Alex
 * @version 1.0
 */
public abstract class Response implements IResponse {

    // Variable for JSONObject custom object
    JSONObject JSONMessage;

    public void setCode(String code) {
        JSONMessage.put("Status", code );
    }
}
