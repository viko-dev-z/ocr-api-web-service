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

import static java.lang.Integer.parseInt;

public class PostageVisitor implements Visitor {
    private String validationResult = "";

    public void visit(GenericParam param) throws ParamsInvalidException {
        validateCommonData(param);
    }

    @Override
    public void visit(ChecksumParam checksumParam) throws ParamsInvalidException {
        validateCommonData(checksumParam);
        if (checksumParam.getValue().toString().length() != 32 ){
            validationResult += checksumParam.getName() + " has invalid length";
        }

        if (!checksumParam.getValue().toString().matches("-?[0-9a-fA-F]+")){
            validationResult += checksumParam.getName() + " has invalid characters: " + checksumParam.getValue().toString();
        }
    }

    @Override
    public void visit(IntParam intParam) throws ParamsInvalidException {
        validateCommonData(intParam);
        int number = -1;
        try {
            number = parseInt(intParam.getValue().toString());
        } catch (NumberFormatException n){
            throw new ParamsInvalidException("The " + intParam.getName() + " is an invalid number character");
        }

        if (number < 0){
            throw new ParamsInvalidException("The " + intParam.getName() + " must be greater than 0");
        }
    }

    String getValidationResult(){
        return validationResult;
    }

    private void validateCommonData(AbstractParam common) throws ParamsInvalidException {
        if (common.getValue().toString().isEmpty()){
            validationResult += "The " + common.getName() + " is empty \n";
            throw new ParamsInvalidException("The " + common.getName() + " is empty \n");
        }
        if (common.getValue() == null){
            validationResult += "The " + common.getName() + " is null \n";
            throw new ParamsInvalidException("The " + common.getName() + " is null \n");
        }
    }
}
