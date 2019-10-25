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

public class RangeValidation implements IValidateStrategy {
    private int minimum;
    private int maximum;

    public RangeValidation(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    public void validate() throws ParamsInvalidException {
        if(this.minimum > this.maximum){
            throw new ParamsInvalidException(this.minimum + " greater than " + this.maximum);
        }
    }
}
