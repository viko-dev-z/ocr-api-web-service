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

public class NullOrEmptyValidation implements IValidateStrategy {
    private String value;

    public NullOrEmptyValidation(String value) {
        this.value = value;
    }

    @Override
    public void validate() throws ParamsInvalidException {
        if (null == this.value || this.value.isEmpty()) {
            throw new ParamsInvalidException(" The entered param is null or empty");
        }
    }
}
