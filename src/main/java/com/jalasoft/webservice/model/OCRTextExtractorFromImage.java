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
import java.util.ArrayList;

public class OCRTextExtractorFromImage implements IConverter {
    private Tesseract tesseract;
    private String textResult;
    private File imageFile;
    private ArrayList<String> supportedLanguages;

    public OCRTextExtractorFromImage() {
        this.tesseract = new Tesseract();
        tesseract.setDatapath("./thirdParty/Tess4J/tessdata");
        loadSupportedLanguages();
    }

    @Override
    public String textExtractor(Criteria criteria) {
        IJSONMessage jsonMessage;
        if(criteria.isSupportedLanguage(supportedLanguages)){
            jsonMessage = new JSONOkMessage();
            textResult = jsonMessage.getMessage(textExtractorForSupportedLanguages(criteria));
        } else {
            jsonMessage = new JSONErrorMessage();
            textResult = jsonMessage.getMessage("Language not supported");
        }
        return textResult;
    }

    private String textExtractorForSupportedLanguages(Criteria criteria){
        String textResultFromValidLang = "";
        try {
            tesseract.setLanguage(criteria.getLang());
            // the path of your tess data folder
            // inside the extracted file
            imageFile = new File(criteria.getFilePath());
            textResultFromValidLang = tesseract.doOCR(imageFile);

            System.out.print(textResultFromValidLang);
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }
        return textResultFromValidLang;
    }

    private void loadSupportedLanguages(){
        supportedLanguages.add("eng");
        supportedLanguages.add("spa");
    }
}
