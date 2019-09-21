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

package com.jalasoft.webservice;

import com.jalasoft.webservice.model.Criteria;
import com.jalasoft.webservice.model.IConverter;
import com.jalasoft.webservice.model.OCRTextExtractorFromImage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        Criteria criteria = new Criteria();
        criteria.setFilePath("/temp/image.png");
        criteria.setLang("eng");
        IConverter textFromImage = new OCRTextExtractorFromImage();
        String textResult = textFromImage.textExtractor(criteria);
        System.out.println(textResult);
    }

}
