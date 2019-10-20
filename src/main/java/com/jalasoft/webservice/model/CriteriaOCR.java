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

import java.util.ArrayList;

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
}

