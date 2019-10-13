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
import com.jalasoft.webservice.controller.params_validation.*;
import com.jalasoft.webservice.error_handler.ConvertException;
import com.jalasoft.webservice.model.*;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static java.lang.Integer.parseInt;

/**
 * Implements REST Endpoint to extract Text from a PDF file
 *
 * @author  Victor
 * @version 1.0
 */

@RestController
@RequestMapping("/api/v1")
public class ProcessPdfRestController extends ProcessAbstractRestController {

    public ProcessPdfRestController() {
    }

    /**
     * In order to invoke this method, please execute the API query
     * Endpoint:
     * http://localhost:8080/api/v1/ocr/processPDF
     *
     * Body:
     * {
     *   "file": "valid PDF file path",
     *   "checksum" : "MD5 Format",
     *   "startPage": "start page to be extracted"
     *   "endPage": "last page to be extracted"
     * }
     *
     * @return a String value with JSON Response Format
     * {
     *     "Status" : "200",
     *     "ExtractedMessage": "Text"
     * }
     */
    @RequestMapping("/ocr/processPDF")
    @PostMapping
    @ResponseBody
    public ResponseEntity processPDFFile(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "checksum") String checksum,
            @RequestParam(value = "startPage") String startPageText,
            @RequestParam(value = "endPage") String endPageText,
            @Value("${imagePath}") String propertyFilePath) {

        ValidateParams params = new ValidateParams();
        params.addParam(new ChecksumParam("checksum", checksum));
        params.addParam(new FileParam("file", file, checksum));
        params.addParam(new IntParam("startPageText", startPageText));
        params.addParam(new IntParam("endPageText", endPageText));
        ResponseEntity result = params.validateParams();

        if (result.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
            return result;
        }

        // Add the file to the temp folder and to the database if not exists
        FileValidator.processFile(propertyFilePath, file, dbm, checksum);

        try {
            // Extracting Text from PDF by using PdfBox and Criteria
            CriteriaPDF pdfCriteria = new CriteriaPDF();
            pdfCriteria.setFilePath(dbm.getPath(checksum));
            pdfCriteria.setStartPage(parseInt(startPageText));
            pdfCriteria.setEndPage(parseInt(endPageText));

            IConverter converter = new PDFConverter();

            myResponses = ResponsesSupported.OK;
            jsonMessage = new ResponseOkMessage();
            jsonMessage.setCode("200");
            jsonMessage = converter.textExtractor(pdfCriteria);
            return processResponse();
        } catch (ConvertException e) {
            e.printStackTrace();
        }
        return processResponse();
    }

}
