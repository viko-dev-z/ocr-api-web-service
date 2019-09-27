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

public class Criteria {
    private String filePath;
    private String lang;

    public Criteria() {
        this.filePath = "";
        this.lang = "eng";
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isSupportedLanguage(ArrayList<String> supportedLanguajes){
        return supportedLanguajes.contains(getLang());
    }
}
