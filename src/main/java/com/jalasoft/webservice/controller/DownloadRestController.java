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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/api/v1")
public class DownloadRestController {


    @GetMapping("/file/{fileName:.+}")
    public void getFile(HttpServletResponse response,
                   @PathVariable("fileName")String fileName,
                    @Value("${downloadPath}") String downloadFilePath) throws IOException {
        String filePath = downloadFilePath + fileName;
        File file = new File(filePath);
        if (file.exists()){
            response.setContentType(FileValidator.getMimeType(filePath));
            InputStream stream = new BufferedInputStream(
              new FileInputStream(file)
            );
            FileCopyUtils.copy(stream, response.getOutputStream());
        }


    }

}
