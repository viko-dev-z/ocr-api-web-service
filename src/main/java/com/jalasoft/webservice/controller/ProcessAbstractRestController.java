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

import com.jalasoft.webservice.controller.params_validation.ValidateParams;
import com.jalasoft.webservice.model.DBManager;

/**
 * Enum to represent a group of constants for supported HTTP Status Code
 */

public abstract class ProcessAbstractRestController {
    DBManager dbm = new DBManager();
    Response jsonMessage;
}
