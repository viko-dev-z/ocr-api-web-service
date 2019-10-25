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

package com.jalasoft.webservice.model.validation;

import com.jalasoft.webservice.error_handler.ParamsInvalidException;

import java.util.List;

public class Context {
    List<IValidateStrategy> strategyList;

    public Context(List<IValidateStrategy> strategies){
        this.strategyList = strategies;
    }

    public void validate() throws ParamsInvalidException {
        for (IValidateStrategy strategy : this.strategyList) {
            strategy.validate();
        }
    }
}
