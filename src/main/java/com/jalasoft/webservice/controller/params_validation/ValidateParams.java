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

import java.util.ArrayList;

public class ValidateParams {
    private ArrayList<Visitable> params;

    public ValidateParams(){
        params = new ArrayList<Visitable>();
    }

    public void addParam(GenericParam param){
        params.add(param);
    }

    public String validateParams() throws ParamsInvalidException {
        PostageVisitor visitor = new PostageVisitor();

        for(Visitable param: params){
            param.accept(visitor);
        }
        String validationResult = visitor.getValidationResult();
        return validationResult;
    }

    public void addParam(ChecksumParam checksum) {
        params.add(checksum);
    }

    public void addParam(IntParam endPageText) {
        params.add(endPageText);
    }
}
