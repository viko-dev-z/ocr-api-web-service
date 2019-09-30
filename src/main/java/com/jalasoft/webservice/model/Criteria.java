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



/**
 * Superclass to manage the Criteria for API parameters.
 *
 * @author Alex and Victor
 * @version 1.0
 */
public abstract class Criteria {

    // path variable that will be used in all subclasses
    private String filePath;

    /**
     * Default constructor
     */
    public Criteria() {
        this.filePath = "";
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
