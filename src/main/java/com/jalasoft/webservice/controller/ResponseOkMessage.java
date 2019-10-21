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

import com.jalasoft.webservice.common.StandardValues;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Subclass that implements methods from IResponse interface
 * and initialize JSONMessage instance variable
 *
 * @author  Alex
 * @version 1.0
 */
public class ResponseOkMessage extends Response {

    /**
     * Default constructor which initialize JSON Message
     * for successful API requests with 200 status code
     */
    public ResponseOkMessage() {
        this.JSONMessage = new JSONObject();
        this.JSONMessage.put(StandardValues.JSON_TAG_TIMESTAMP, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public String getJSON() {
        return JSONMessage.toString(4);
    }

    @Override
    public void setMessage(String message) {
        JSONMessage.put(StandardValues.JSON_TAG_EXTRACTED_MESSAGE, message);
    }

    @Override
    public void setDownloadURL(String path) {
        JSONMessage.put(StandardValues.JSON_TAG_DOWNLOAD_URL, path);
    }
}
