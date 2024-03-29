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
import com.jalasoft.webservice.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Enum to represent a group of constants for supported HTTP Status Code
 */
enum ResponsesSupported {
    OK,
    LANG_UNSUPPORTED,
    FILE_UNSUPPORTED
}

/**
 * Implements REST Endpoint to extract Text from Image
 *
 * @author  Victor
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1")
public class ProcessImageRestController {
    DBManager dbm = new DBManager();
    ResponsesSupported myResponses = ResponsesSupported.OK;
    Response jsonMessage;
    private String textConverted;

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
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "language", defaultValue = "eng")
                           String language,
            @RequestParam(value = "checksum") String checksum,
            @Value("${imagePath}") String propertyFilePath) {

        try {
            // we check if there is a file with same checksum
            String filePath = dbm.getPath(checksum);

            if (filePath == null){
                filePath = propertyFilePath + file.getOriginalFilename();
                // validate if file is an image.
                if (! FileValidator.isValidImage(filePath)){
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
            // Extracting Text from Image by using Tess4j and Criteria
            CriteriaOCR imageCriteria = new CriteriaOCR();
            imageCriteria.setFilePath(filePath);
            if (imageCriteria.isSupportedLanguage(language)){
                myResponses = ResponsesSupported.OK;
                imageCriteria.setLang(language);
                IConverter converter = new OCRTextExtractorFromImage();
                jsonMessage = converter.textExtractor(imageCriteria);
                return processResponse();
            }
            else {
                myResponses = ResponsesSupported.LANG_UNSUPPORTED;
                return processResponse();
            }

        } catch (IOException e) {
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
                        .body(jsonMessage.getJSON());
                break;
            case LANG_UNSUPPORTED:
                jsonMessage = new ResponseErrorMessage();
                jsonMessage.setCode("400");
                jsonMessage.setMessage("Not a Valid Language property");
                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(jsonMessage.getJSON());
                break;
            case FILE_UNSUPPORTED:
                jsonMessage = new ResponseErrorMessage();
                jsonMessage.setCode("415");
                jsonMessage.setMessage("Not a Valid Image Format");
                response = ResponseEntity
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body(jsonMessage.getJSON());
                break;
        }
        return response;
    }

}
