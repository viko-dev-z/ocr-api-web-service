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

public class ResponseOkMessage extends Response {

    public ResponseOkMessage() {
        this.JSONMessage = new JSONObject();
        this.JSONMessage.put("Status", "200");
    }

    @Override
    public String getMessage() {
        return JSONMessage.toString();
    }

    @Override
    public void setMessage(String message) {
        JSONMessage.put("ExtractedMessage", message);
    }
}
