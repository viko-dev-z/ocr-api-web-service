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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;

public class PDFConverter implements IConverter{

    private PDFTextStripper textStripper;
    private File documentFile;
    private PDDocument document;

    public PDFConverter() {
    }

    @Override
    public Response textExtractor(Criteria criteria) throws ConvertException {
        Response jsonMessage = new ResponseOkMessage();
        jsonMessage.setCode("200");
        try {
            this.textStripper = new PDFTextStripper();
            jsonMessage.setMessage(textExtractorForPageRange((CriteriaPDF) criteria));
        }
        catch (Exception e) {
            throw new ConvertException("message2", e);
        }
        return jsonMessage;
    }

    private String textExtractorForPageRange(CriteriaPDF criteria) throws ConvertException {
        String pdfFileInText = "";
        StringBuilder content = new StringBuilder();
        try {
            documentFile = new File(criteria.getFilePath());
            document = PDDocument.load(documentFile);
            String[] lines;
            if ((criteria.getEndPage() != -1) && (criteria.getEndPage() != -1)){
                textStripper.setStartPage(criteria.getStartPage());
                textStripper.setEndPage(criteria.getEndPage());
            }
            if (!document.isEncrypted()) {
                pdfFileInText = textStripper.getText(document);
                lines = pdfFileInText.split("\\r\\n\\r\\n");
                for (String line : lines) {
                    System.out.println(line);
                    content.append(line);
                }
            }
        }
        catch (Exception e) {
            throw new ConvertException("message", e);
        }
//        catch (IOException ex) {
//            throw new ConvertException("message2", ex);
//
//        }
        return content.toString();
    }
}
