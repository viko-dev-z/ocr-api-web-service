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
import com.jalasoft.webservice.error_handler.ConvertException;
import com.jalasoft.webservice.model.CriteriaPDF;
import com.jalasoft.webservice.model.DBManager;


import com.jalasoft.webservice.model.IConverter;
import com.jalasoft.webservice.model.PDFConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Integer.parseInt;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Implements REST Endpoint to extract Text from a PDF file
 *
 * @author  Victor
 * @version 1.0
 */

@RestController
@RequestMapping("/api/v1")
public class ProcessPdfRestController {
    DBManager dbm = new DBManager();
    ResponsesSupported myResponses = ResponsesSupported.OK;
    Response jsonMessage;
    private String textConverted;

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

        int startPage = parseInt(startPageText);
        int endPage = parseInt(endPageText);

        // we check if there is a file with same checksum
        String filePath = dbm.getPath(checksum);
        //String fileChecksum = FileValidator.getFileChecksum(filePath);
        try {
            if (filePath == null){
                filePath = propertyFilePath + file.getOriginalFilename();
                // validate if file is pdf.
                if (! FileValidator.isValidPDFFile(filePath)){
                    myResponses = ResponsesSupported.FILE_UNSUPPORTED;
                    return processResponse();
                }
                else {
                    // file is saved in path of application.properties
                    dbm.addFile(checksum, filePath);
                    Path location = Paths.get(filePath);
                    Files.copy(file.getInputStream(), location,
                            REPLACE_EXISTING);
                }
            }

            // Extracting Text from PDF by using PdfBox and Criteria
            CriteriaPDF pdfCriteria = new CriteriaPDF();
            pdfCriteria.setFilePath(filePath);
            pdfCriteria.setStartPage(startPage);
            pdfCriteria.setEndPage(endPage);

            IConverter converter = new PDFConverter();

            myResponses = ResponsesSupported.OK;
            jsonMessage = new ResponseOkMessage();
            jsonMessage.setCode("200");
            jsonMessage =  converter.textExtractor(pdfCriteria);
            return processResponse();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (
        ConvertException e) {
            e.printStackTrace();
        }
        return processResponse();
    }

    /**
     * Process a Response Body according to Enum value
     * assigned in processImage method.
     */
    private ResponseEntity processResponse() {
        ResponseEntity response = null;
        switch(myResponses) {
            case OK:
                response = ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .body(jsonMessage.getJSON());
                break;
            case LANG_UNSUPPORTED:
                jsonMessage = new ResponseErrorMessage();
                jsonMessage.setCode("400");
                jsonMessage.setMessage("Not a Valid Language property");
                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .body(jsonMessage.getJSON());
                break;
            case FILE_UNSUPPORTED:
                jsonMessage = new ResponseErrorMessage();
                jsonMessage.setCode("415");
                jsonMessage.setMessage("Not a Valid PDF Format");
                response = ResponseEntity
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .body(jsonMessage.getJSON());
                break;
        }
        return response;
    }
}
