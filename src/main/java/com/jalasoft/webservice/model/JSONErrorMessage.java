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

package com.jalasoft.webservice.model;

import org.json.JSONObject;

public class JSONErrorMessage extends AbstractJSONMessage {
    public JSONErrorMessage() {
        this.JSONMessage = new JSONObject();
    }

    @Override
    public String getMessage(String message) {
        JSONMessage.put("Status", "400");
        JSONMessage.put("Message", message);
        return JSONMessage.toString();
    }
}
