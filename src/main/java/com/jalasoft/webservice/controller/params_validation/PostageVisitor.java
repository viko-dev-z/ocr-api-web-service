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
import com.jalasoft.webservice.common.FileValidator;
import com.jalasoft.webservice.common.ResponseBuilder;
import com.jalasoft.webservice.common.StandardValues;
import com.jalasoft.webservice.error_handler.ParamsInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class PostageVisitor implements Visitor {
    private String message;
    private ResponseEntity validationResponseEntity;

    public PostageVisitor(){
        validationResponseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).body("Accepted");
    }

    public void visit(GenericParam param) throws ParamsInvalidException {
        validateCommonData(param);
    }

    @Override
    public void visit(ChecksumParam checksumParam) throws ParamsInvalidException {
        validateCommonData(checksumParam);
        if (checksumParam.getValue().toString().length() != StandardValues.CHECKSUM_SIZE ){
            message = "The " + checksumParam.getName() + " has an invalid length.";
            validationResponseEntity = ResponseBuilder.getResponse(HttpStatus.BAD_REQUEST, message);
            throw new ParamsInvalidException(message);
        }

        if (!checksumParam.getValue().toString().matches(StandardValues.REGULAR_EXPRESSION_HEX_NUMBERS)){
            message = "The " + checksumParam.getName() + " has an invalid characters: " + checksumParam.getValue().toString();
            validationResponseEntity = ResponseBuilder.getResponse(HttpStatus.BAD_REQUEST, message);
            throw new ParamsInvalidException(message);
        }
    }

    @Override
    public void visit(IntParam intParam) throws ParamsInvalidException {
        validateCommonData(intParam);
        int number = StandardValues.MINUS_ONE;
        try {
            number = parseInt(intParam.getValue().toString());
        } catch (NumberFormatException n){
            message = "The " + intParam.getName() + " is and invalid number character: " + intParam.getValue().toString();
            validationResponseEntity = ResponseBuilder.getResponse(HttpStatus.BAD_REQUEST, message);
            throw new ParamsInvalidException(message);
        }

        if (number <= StandardValues.ZERO){
            message = "The " + intParam.getName() + " must be greater than 0";
            validationResponseEntity = ResponseBuilder.getResponse(HttpStatus.BAD_REQUEST, message);
            throw new ParamsInvalidException(message);
        }

    }

    @Override
    public void visit(FileParam fileParam) throws ParamsInvalidException {
        validateCommonData(fileParam);
        MultipartFile theFile = (MultipartFile)fileParam.getValue();
        if (!FileValidator.isValidPDFFile(theFile.getOriginalFilename())){
            message = "The " + fileParam.getName() + " is not a PDF file name: " + fileParam.getValue().toString();
            validationResponseEntity = ResponseBuilder.getResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, message);
            throw new ParamsInvalidException(message);
        }
        String theFileChecksum = StandardValues.EMPTY_STRING;
        try {
            theFileChecksum = FileValidator.getFileChecksum(theFile.getInputStream());
        } catch (IOException e) {
            message = "Error to generate the file checksum";
            validationResponseEntity = ResponseBuilder.getResponse(HttpStatus.BAD_REQUEST, message);
            throw new ParamsInvalidException(message);
        }

        if (!theFileChecksum.equalsIgnoreCase(fileParam.getInputChecksum())){
            message = "File checksum does not match. "
                    + "Expected: " + theFileChecksum
                    + " - Actual: " + fileParam.getInputChecksum();
            validationResponseEntity = ResponseBuilder.getResponse(HttpStatus.BAD_REQUEST, message);
            throw new ParamsInvalidException(message);
        }
    }

    @Override
    public void visit(IntParam startPage, IntParam endPage) throws ParamsInvalidException {
        visit(startPage);
        visit(endPage);
        int startP = parseInt(startPage.getValue().toString());
        int endP = parseInt(endPage.getValue().toString());

        if (startP > endP) {
            message = "The " + startPage.getName() + " must be less than the " + endPage.getName();
            validationResponseEntity = ResponseBuilder.getResponse(HttpStatus.BAD_REQUEST, message);
            throw  new ParamsInvalidException(message);
        }
    }

    public ResponseEntity getValidationResponseEntity(){
        return  validationResponseEntity;
    }

    private void validateCommonData(AbstractParam common) throws ParamsInvalidException {
        if (common.getValue().toString().isEmpty()){
            message = "The " + common.getName() + " is empty";
            validationResponseEntity = ResponseBuilder.getResponse(HttpStatus.BAD_REQUEST, message);
            throw new ParamsInvalidException(message);
        }
        if (common.getValue() == null){
            message = "The " + common.getName() + " is null";
            validationResponseEntity = ResponseBuilder.getResponse(HttpStatus.BAD_REQUEST, message);
            throw new ParamsInvalidException(message);
        }
    }
}
