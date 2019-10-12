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

package com.jalasoft.webservice.common;

import com.jalasoft.webservice.controller.IResponse;
import com.jalasoft.webservice.controller.ResponseErrorMessage;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    private static IResponse jsonMessage;
    private static ResponseEntity responseEntity;

    public static ResponseEntity getResponse(String message) {
        ResponseEntity responseEntity = null;
        if (!message.isEmpty()) {
            jsonMessage = ResponseBuilder.getJSONResponse(message);
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(jsonMessage.getJSON());
        }
        return responseEntity;
    }

    private static IResponse getJSONResponse(String message){
        IResponse jsonMessage = null;
        if (!message.isEmpty()) {
            jsonMessage = new ResponseErrorMessage();
            jsonMessage.setCode("400");
            jsonMessage.setMessage(message);
        }
        return jsonMessage;
    }
}
