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

/**
* Interface for Response implementation
*
* @author Alex
* @version 1.0
*/
public interface IResponse {

    // abstract method for returning a String message in JSON format
    String getJSON();

    // abstract method to initialize code JSON property
    void setCode(String code);

    // abstract method to initialize message JSON property
    void setMessage(String message);
}
