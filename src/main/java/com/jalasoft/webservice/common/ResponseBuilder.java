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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    private static ResponseEntity validationResponseEntity;

    public static ResponseEntity getResponse(HttpStatus httpCode, String message) {
        IResponse response;
        if (httpCode.value() >= HttpStatus.BAD_REQUEST.value()){
            response = new ResponseErrorMessage();
            response.setCode(httpCode.toString());
            response.setMessage(message);
            validationResponseEntity = ResponseEntity.status(httpCode)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .body(response.getJSON());
        }

        return validationResponseEntity;
    }
}
