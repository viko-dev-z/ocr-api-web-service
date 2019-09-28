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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/v1")
public class ProcessImageRestController {
    DBManager dbm = new DBManager();

    public ProcessImageRestController() {
    }

    @RequestMapping("/ocr/processImage")
    @PostMapping
    public String extractText(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "language", defaultValue = "eng") String language,
            @RequestParam(value = "checksum") String checksum) {

        String responseResult = null;
        int random = (int)(Math.random() * 100 + 1);
        try {
            // first we save the image file into a local machine path
            String filePath = dbm.getPath(checksum);
            if (filePath == null){
                filePath = "./temp/_image" + random + "_"  + file.getOriginalFilename();
                dbm.addFile(checksum, filePath);
                Path location = Paths.get(filePath);
                Files.copy(file.getInputStream(), location); // standard copy, replace existing mode
            }
            // here we proceed to use the extract Text from Image functionality by using Tesseract wrapper and Criteria
            Criteria imageCriteria = new CriteriaOCR();
            imageCriteria.setFilePath(filePath);
            imageCriteria.setLang(language);
            IConverter extractor = new OCRTextExtractorFromImage();
            responseResult = extractor.textExtractor(imageCriteria).getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseResult;
    }
}
