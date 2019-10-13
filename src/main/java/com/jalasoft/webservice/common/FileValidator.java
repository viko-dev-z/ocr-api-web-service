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

import com.jalasoft.webservice.model.DBManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
    public static boolean isValidImage(String filePath) {
        Path path = new File(filePath).toPath();
        String mimeType = "";
        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String type = mimeType.split("/")[0];
        if (type.equals("image"))
            return true;
        else
            return false;
    }

    /**
     * Returns true if a file is a PDF file, otherwise returns false.
     */
    public static boolean isValidPDFFile(String filePath) {
        Path path = new File(filePath).toPath();
        String mimeType = "";
        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //String type = mimeType.split("/")[0];
        if (mimeType.equals("application/pdf"))
            return true;
        else
            return false;
    }

    public static String getMimeType(String filePath) {
        Path path = new File(filePath).toPath();
        String mimeType = "";
        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mimeType;
    }

    public static String getFileChecksum(InputStream fileInputStream) {
        String checksumMD5 = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            checksumMD5 = checksum(fileInputStream, md);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return checksumMD5;
    }

    private static String checksum(InputStream fileInputStream, MessageDigest md) {

        // DigestInputStream is better, but you also can hash file like this.
        try {
            byte[] buffer = new byte[1024];
            int nread;
            while ((nread = fileInputStream.read(buffer)) != -1) {
                md.update(buffer, 0, nread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static void processFile(String propertyFilePath, MultipartFile file, DBManager dbm, String checksum) {
        // we check if there is a file with same checksum
        String filePath = dbm.getPath(checksum);

        if (filePath == null){
            filePath = propertyFilePath + file.getOriginalFilename();
            // file is saved in path of application.properties
            dbm.addFile(checksum, filePath);
            Path location = Paths.get(filePath);
            try {
                Files.copy(file.getInputStream(), location, REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
