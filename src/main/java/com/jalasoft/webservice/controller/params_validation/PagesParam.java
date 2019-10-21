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

import com.jalasoft.webservice.error_handler.ParamsInvalidException;

public class PagesParam extends AbstractParam {
    private IntParam startPage;
    private IntParam endPage;

    public PagesParam(IntParam start, IntParam end){
        this.startPage = start;
        this.endPage = end;
    }

    public void accept(Visitor visitor) throws ParamsInvalidException {
        visitor.visit(startPage, endPage);
    }
}
