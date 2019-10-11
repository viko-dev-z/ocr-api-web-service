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

package com.jalasoft.webservice.error_handler;

public class ParamsInvalidException extends Exception{
    String message;
    String validationMessage;

    public ParamsInvalidException(String validationMessage){
        this.validationMessage = validationMessage;
    }

    public String getMessage(){
        this.message = validationMessage;
        return this.message;
    }
}
