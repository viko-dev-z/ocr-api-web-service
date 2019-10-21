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

package com.jalasoft.webservice.controller;


import com.jalasoft.webservice.common.FileValidator;
import com.jalasoft.webservice.common.ResponseBuilder;
import com.jalasoft.webservice.common.StandardValues;
import com.jalasoft.webservice.controller.params_validation.*;
import com.jalasoft.webservice.error_handler.ConvertException;
import com.jalasoft.webservice.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


/**
 * Implements REST Endpoint to extract Text from Image
 *
 * @author  Victor
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1")
public class ProcessImageRestController extends ProcessAbstractRestController {

    public ProcessImageRestController() {
    }

    /**
     * In order to invoke this method, please execute the API query
     * Endpoint:
     * http://localhost:8080/api/v1/ocr/processImage
     *
     * Body:
     * {
     *   "file": "valid image path",
     *   "language" : "eng/spa",
     *   "checksum" : "MD5 Format"
     * }
     *
     * @return a String value with JSON Response Format
     * {
     *     "Status" : "200",
     *     "ExtractedMessage": "Text"
     * }
     */
    @RequestMapping("/ocr/processImage")
    @PostMapping
    @ResponseBody
    public ResponseEntity processImage(
            @RequestParam(value = StandardValues.FILE_NAME) MultipartFile file,
            @RequestParam(value = StandardValues.LANGUAGE) String language,
            @RequestParam(value = StandardValues.CHECKSUM_NAME) String checksum) {

        // Validate the input params
        ValidateParams validateParams = new ValidateParams();
        validateParams.addParam(new FileParam(file, checksum, StandardValues.MIME_TYPE_IMAGE));
        validateParams.addParam(new LanguageParam(language));
        validateParams.addParam(new ChecksumParam(checksum));

        ResponseEntity result = validateParams.validateParams();

        if (result.getStatusCode().value() >= HttpStatus.BAD_REQUEST.value()) {
            return result;
        }

        // Add the file to the temp folder and to the database if not exists
        FileValidator.processFile(StandardValues.PROPERTY_FILE_PATH, file, dbm, checksum);
        String filePath = StandardValues.PROPERTY_FILE_PATH + file.getOriginalFilename();
        CriteriaOCR imageCriteria = new CriteriaOCR();
        imageCriteria.setFilePath(filePath);
        imageCriteria.setLang(language);

        try {
            // Extracting Text from Image by using Tess4j and Criteria
            IConverter converter = new OCRConverter() ;
            jsonMessage = converter.textExtractor(imageCriteria);
        } catch (ConvertException e) {
            e.printStackTrace();
        }

        return ResponseBuilder.getResponse(HttpStatus.OK, jsonMessage);
    }
}
