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


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/v1")
public class ProcessImageRestController {

    public ProcessImageRestController() {
    }

    @RequestMapping("/ocr/processImage")
    @PostMapping
    public String extractText(
            @RequestParam(value="file") MultipartFile file,
            @RequestParam(value="language", defaultValue="eng") String language) {
        try{
        String filePath="D:/temp/_image_"+file.getOriginalFilename();
        Path location = Paths.get(filePath);
        Files.copy(file.getInputStream(),location);
// Alex uncomment these lines once you implement OCRModel.java logic
//      OCRModel OCR = new OCRModel(filePath, language);
//      String result  = OCR.extract();
        } catch (IOException e) {
            e.printStackTrace();
        }
//      retun result;
        return "Done..";
    }
}
