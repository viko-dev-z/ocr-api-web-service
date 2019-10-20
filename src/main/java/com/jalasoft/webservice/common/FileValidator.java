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
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
        return getMimeType(filePath).contains(StandardValues.MIME_TYPE_IMAGE);
    }

    /**
     * Returns true if a file is a PDF file, otherwise returns false.
     */
    public static boolean isValidPDFFile(String filePath) {
        return getMimeType(filePath).equals(StandardValues.MIME_TYPE_APPLICATION_PDF);
    }

    /**
     * Return true if file type is in the mime type otherwise false.
     * @param filePath path of the file.
     * @param fileType the file type
     * @return boolean value
     */
    public static boolean isValidFile(String filePath, String fileType) {
        return getMimeType(filePath).contains(fileType);
    }

    public static String getMimeType(String filePath) {
        Path path = new File(filePath).toPath();
        String mimeType = StandardValues.EMPTY_STRING;
        try {
            mimeType = Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mimeType;
    }

    public static String getFileChecksum(InputStream fileInputStream) {
        String checksumMD5 = StandardValues.EMPTY_STRING;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(StandardValues.MD5);
            checksumMD5 = checksum(fileInputStream, md);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return checksumMD5;
    }

    private static String checksum(InputStream fileInputStream, MessageDigest md) {

        // DigestInputStream is better, but you also can hash file like this.
        try {
            byte[] buffer = new byte[StandardValues.BYTE];
            int nread;
            while ((nread = fileInputStream.read(buffer)) != StandardValues.MINUS_ONE) {
                md.update(buffer, StandardValues.ZERO, nread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format(StandardValues.FORMAT, b));
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

    public static String writeToFile(String text, String targetFilePath) throws IOException
    {
        Path targetPath = Paths.get(targetFilePath);
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        Path path = Files.write(targetPath, bytes,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING );
        return path.getFileName().toString();
    }
}
