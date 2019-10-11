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

import com.jalasoft.webservice.controller.Response;
import com.jalasoft.webservice.controller.ResponseOkMessage;
import com.jalasoft.webservice.error_handler.ConvertException;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.io.File;
import java.io.IOException;

/**
 * Class to manage tesseract library.
 *
 * @author Alex
 * @version 1.0
 */
public class OCRConverter implements IConverter {

    private Tesseract tesseract;
    private File imageFile;

    /**
     * Default constructor which initialize Tesseract library
     */
    public OCRConverter() {
        this.tesseract = new Tesseract();
        tesseract.setDatapath("./thirdParty/Tess4J/tessdata");
    }

    @Override
    public Response textExtractor(Criteria criteria) throws ConvertException {
        Response jsonMessage = new ResponseOkMessage();
        jsonMessage.setCode("200");
        try {
        jsonMessage.setMessage(textExtractorForSupportedLanguages((CriteriaOCR) criteria));
        }
        catch (Exception e) {
            throw new ConvertException("message2", e);
        }
        return jsonMessage;
    }

    private String textExtractorForSupportedLanguages(CriteriaOCR criteria) throws ConvertException {
        String textResultFromValidLang = "";
        try {
            tesseract.setLanguage(criteria.getLang());
            imageFile = new File(criteria.getFilePath());
            textResultFromValidLang = tesseract.doOCR(imageFile);
            System.out.print(textResultFromValidLang);
        }
        catch (TesseractException e) {
            throw new ConvertException("message", e);
        }
//        catch (IOException ex) {
//            throw new ConvertException("message2", ex);
//
//        }
        return textResultFromValidLang;
    }

}
