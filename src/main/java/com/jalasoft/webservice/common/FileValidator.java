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

package com.jalasoft.webservice.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Static class to validate Files
 *
 * @author Victor
 * @version 1.0
 */
public final class FileValidator {

    // constructor private to prevent instantiation
    private FileValidator() {
    }

    /**
     * Returns true if a file is an Image, otherwise returns false.
     */
    public static boolean isValidImage(String filePath){
        Path path = new File(filePath).toPath();
        String mimeType = "";
        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String type = mimeType.split("/")[0];
        if(type.equals("image"))
            return true;
        else
            return false;
    }

    public static String getMimeType(String filePath){
        Path path = new File(filePath).toPath();
        String mimeType = "";
        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mimeType;

    }
}
