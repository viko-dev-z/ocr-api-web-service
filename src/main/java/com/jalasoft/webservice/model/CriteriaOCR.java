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

package com.jalasoft.webservice.model;

import com.jalasoft.webservice.error_handler.ParamsInvalidException;
import com.jalasoft.webservice.model.validation.Context;
import com.jalasoft.webservice.model.validation.IValidateStrategy;
import com.jalasoft.webservice.model.validation.LangValidation;
import com.jalasoft.webservice.model.validation.NullOrEmptyValidation;

import java.util.ArrayList;
import java.util.List;

/**
 * Subclass to manage the API Request Body parameters.
 *
 * @author Alex and Victor
 * @version 1.0
 */
public class CriteriaOCR extends Criteria {

    // lang variable that will be used in all subclasses
    private String lang;

    /**
     * Default constructor which initialize the supported languages
     */
    public CriteriaOCR() {
        super();
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void validate() throws ParamsInvalidException {
        List<IValidateStrategy> values = new ArrayList<>();
        values.add(new NullOrEmptyValidation(this.lang));
        values.add(new LangValidation(this.lang));
        values.add(new NullOrEmptyValidation(this.getFilePath()));

        Context context = new Context(values);
        context.validate();
    }
}

