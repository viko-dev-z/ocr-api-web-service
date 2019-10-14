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

import com.jalasoft.webservice.model.DBManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Enum to represent a group of constants for supported HTTP Status Code
 */
enum ResponsesSupported {
    OK,
    LANG_UNSUPPORTED,
    FILE_UNSUPPORTED
}
public class ProcessAbstractRestController {
    DBManager dbm = new DBManager();
    ResponsesSupported responsesSupported = ResponsesSupported.OK;
    Response jsonMessage;

    /**
     * Process a Response Body according to Enum value
     * assigned in processImage method.
     */
    public ResponseEntity processResponse() {
        ResponseEntity response = null;
        switch(responsesSupported) {
            case OK:
                response = ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .body(jsonMessage.getJSON());
                break;
            case LANG_UNSUPPORTED:
                jsonMessage = new ResponseErrorMessage();
                jsonMessage.setCode("400");
                jsonMessage.setMessage("Not a Valid Language property");
                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .body(jsonMessage.getJSON());
                break;
            case FILE_UNSUPPORTED:
                jsonMessage = new ResponseErrorMessage();
                jsonMessage.setCode("415");
                jsonMessage.setMessage("Not a Valid PDF Format");
                response = ResponseEntity
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .body(jsonMessage.getJSON());
                break;
        }
        return response;
    }
}
