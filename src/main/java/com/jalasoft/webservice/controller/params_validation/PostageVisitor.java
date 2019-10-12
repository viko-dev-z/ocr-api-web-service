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
    private StringBuilder validationResult;
    private String message;

    public PostageVisitor(){
        validationResult = new StringBuilder();
    }

    public void visit(GenericParam param) throws ParamsInvalidException {
        validateCommonData(param);
    }

    @Override
    public void visit(ChecksumParam checksumParam) throws ParamsInvalidException {
        validateCommonData(checksumParam);
        if (checksumParam.getValue().toString().length() != 32 ){
            message = "The " + checksumParam.getName() + " has an invalid length. \n";
            validationResult.append(message);
            throw new ParamsInvalidException(message);
        }

        if (!checksumParam.getValue().toString().matches("-?[0-9a-fA-F]+")){
            message = "The " + checksumParam.getName() + " has an invalid characters: " + checksumParam.getValue().toString() + "\n";
            validationResult.append(message);
            throw new ParamsInvalidException(message);
        }
    }

    @Override
    public void visit(IntParam intParam) throws ParamsInvalidException {
        validateCommonData(intParam);
        int number = -1;
        try {
            number = parseInt(intParam.getValue().toString());
        } catch (NumberFormatException n){
            message = "The " + intParam.getName() + " is and invalid number character: " + intParam.getValue().toString();
            validationResult.append(message);
            throw new ParamsInvalidException(message);
        }

        if (number < 0){
            message = "The " + intParam.getName() + " must be greater than 0";
            validationResult.append(message);
            throw new ParamsInvalidException(message);
        }
    }

    String getValidationResult(){
        return validationResult.toString();
    }

    private void validateCommonData(AbstractParam common) throws ParamsInvalidException {
        if (common.getValue().toString().isEmpty()){
            message = "The " + common.getName() + " is empty \n";
            validationResult.append(message);
            throw new ParamsInvalidException(message);
        }
        if (common.getValue() == null){
            message = "The " + common.getName() + " is null \n";
            validationResult.append(message);
            throw new ParamsInvalidException(message);
        }
    }
}
