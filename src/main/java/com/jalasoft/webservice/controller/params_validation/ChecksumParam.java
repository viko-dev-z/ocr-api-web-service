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

package com.jalasoft.webservice.controller.params_validation;

import com.jalasoft.webservice.common.StandardValues;
import com.jalasoft.webservice.error_handler.ParamsInvalidException;

public class ChecksumParam extends AbstractParam  {

    public ChecksumParam(Object value){
        setName(StandardValues.CHECKSUM_NAME);
        setValue(value);
    }

    public void accept(Visitor visitor) throws ParamsInvalidException {
        visitor.visit(this);
    }
}
