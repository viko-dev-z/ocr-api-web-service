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

import java.util.ArrayList;
import java.util.List;

public class LangValidation implements IValidateStrategy {
    private List<String> langs;
    private String value;

    public LangValidation(String value) {
        this.value = value;
        this.langs = new ArrayList<>();
        this.langs.add("eng");
        this.langs.add("spa");
    }

    @Override
    public void validate() throws ParamsInvalidException {
        if (!this.langs.contains(this.value)){
            throw new ParamsInvalidException("The language \"" + this.value + "\" is not supported");
        }
    }
}
