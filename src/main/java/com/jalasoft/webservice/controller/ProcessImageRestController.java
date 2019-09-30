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


import com.jalasoft.webservice.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
    public String extractText(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "language", defaultValue = "eng")
                           String language,
            @RequestParam(value = "checksum") String checksum,
            @Value("${imagePath}") String propertyFilePath) {

        String result = null;
        try {
            // we check if there is a file with same checksum
            String filePath = dbm.getPath(checksum);
            if (filePath == null){
                // file is saved in path configured in application.properties
                filePath = propertyFilePath + file.getOriginalFilename();
                dbm.addFile(checksum, filePath);
                Path location = Paths.get(filePath);
                Files.copy(file.getInputStream(), location, REPLACE_EXISTING);

            }
            // Extracting Text from Image by using Tess4j and Criteria
            CriteriaOCR imageCriteria = new CriteriaOCR();
            imageCriteria.setFilePath(filePath);
            imageCriteria.setLang(language);
            IConverter extractor = new OCRTextExtractorFromImage();
            result = extractor.textExtractor(imageCriteria).getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
