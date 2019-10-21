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

import com.jalasoft.webservice.common.FileValidator;
import com.jalasoft.webservice.common.ResponseBuilder;
import com.jalasoft.webservice.common.StandardValues;
import com.jalasoft.webservice.controller.Response;
import com.jalasoft.webservice.controller.ResponseOkMessage;
import com.jalasoft.webservice.error_handler.ConvertException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class PDFConverter implements IConverter{

    private PDFTextStripper textStripper;
    private File documentFile;
    private PDDocument document;
    private String downloadPath;

    public PDFConverter() {
    }

    @Override
    public Response textExtractor(Criteria criteria) throws ConvertException {
        Response jsonMessage = new ResponseOkMessage();

        String textExtracted = textExtractorForPageRange((CriteriaPDF) criteria);
        createTxtFile(textExtracted, ((CriteriaPDF) criteria).getDownloadPath());

        jsonMessage.setMessage(textExtracted);
        jsonMessage.setDownloadURL(downloadPath);

        return jsonMessage;
    }

    private String textExtractorForPageRange(CriteriaPDF criteria) throws ConvertException {
        String pdfFileInText = StandardValues.EMPTY_STRING;
        StringBuilder content = new StringBuilder();
        try {
            this.textStripper = new PDFTextStripper();
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
        return content.toString();
    }

    private void createTxtFile(String textExtracted, String downloadPath){
        String fileName = "";
        try {
            fileName = FileValidator.writeToFile(textExtracted, downloadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        URI downloadLocation = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/api/v1/file/{fileName}")
                .build()
                .expand(fileName)
                .toUri();
        this.downloadPath = downloadLocation.toString();
    }
}
