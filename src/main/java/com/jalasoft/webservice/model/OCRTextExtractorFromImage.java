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
    private File imageFile;
    private ArrayList<String> supportedLanguages;

    public OCRTextExtractorFromImage() {
        this.tesseract = new Tesseract();
        tesseract.setDatapath("./thirdParty/Tess4J/tessdata");
        supportedLanguages = new ArrayList<String>();
        loadSupportedLanguages();
    }

    @Override
    public Response textExtractor(Criteria criteria) {
        Response jsonMessage;
        if(criteria.isSupportedLanguage(supportedLanguages)){
            jsonMessage = new ResponseOkMessage();
            jsonMessage.setMessage(textExtractorForSupportedLanguages(criteria));
        } else {
            jsonMessage = new ResponseErrorMessage();
            jsonMessage.setMessage("Language not supported");
        }
        return jsonMessage;
    }

    private String textExtractorForSupportedLanguages(Criteria criteria){
        String textResultFromValidLang = "";
        try {
            tesseract.setLanguage(criteria.getLang());
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
