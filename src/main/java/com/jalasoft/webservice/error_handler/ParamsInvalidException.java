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
    int code;
    String message;
    String param;

    public ParamsInvalidException(int code, String param){
        this.code = code;
        this.param = param;
    }

    public String getMessage(){
        switch (this.code){
            case 10:
                this.message = "the pram " + this.param + " is null";
                break;
            case 11:
                this.message = "the param " + this.param + " is empty";
                break;
        }
        return message;
    }
}
