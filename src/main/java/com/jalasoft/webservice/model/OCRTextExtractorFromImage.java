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

package com.jalasoft.webservice.model;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCRTextExtractorFromImage implements IConverter {
    private Tesseract tesseract;
    private String defaultTessData;
    private String textResult;
    private File imageFile;

    public OCRTextExtractorFromImage() {
        this.tesseract = new Tesseract();
        this.defaultTessData = "./thirdParty/Tess4J/tessdata";
    }

    @Override
    public String textExtractor(Criteria criteria) {
        try {
            tesseract.setDatapath(defaultTessData);
            tesseract.setLanguage(criteria.getLang());
            // the path of your tess data folder
            // inside the extracted file
            imageFile = new File(criteria.getFilePath());
            textResult = tesseract.doOCR(imageFile);

            // path of your image file
            System.out.print(textResult);
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }
        return textResult;
    }
}
