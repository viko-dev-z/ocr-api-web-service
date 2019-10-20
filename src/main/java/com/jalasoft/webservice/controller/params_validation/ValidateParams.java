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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class ValidateParams {
    private ArrayList<Visitable> params;
    private ResponseEntity responseEntity;

    public ValidateParams(){
        params = new ArrayList<Visitable>();
        this.responseEntity = null;
    }

    public ResponseEntity validateParams() {
        PostageVisitor visitor = new PostageVisitor();

        for(Visitable param: params){
            try {
                param.accept(visitor);
            } catch (ParamsInvalidException paramIE){
                //paramIE.printStackTrace();
                break;
            }
        }
        return visitor.getValidationResponseEntity();
    }

    public void addParam(PagesParam pagesParam) {
        params.add(pagesParam);
    }

    public void addParam(AbstractParam languageParam) { params.add(languageParam); }
}
