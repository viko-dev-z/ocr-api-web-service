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
import com.jalasoft.webservice.controller.Response;
import com.jalasoft.webservice.controller.ResponseErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    private static ResponseEntity validationResponseEntity;

    /**
     * This method constructs a ResponseEntity with the parameters.
     * According with httpCode parameter it will generate a 400 response or 200 response.
     * @param httpCode defines the type of response to build.
     * @param response contains the message that will displayed in the response.
     * @return a ResponseEntity object with the respective http code and its message.
     */
    public static ResponseEntity getResponse(HttpStatus httpCode, IResponse response) {
        response.setCode(httpCode.toString());
        return ResponseEntity.status(httpCode)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .body(response.getJSON());
    }
}
