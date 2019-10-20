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

import java.util.ArrayList;
import java.util.Arrays;

public final class StandardValues {
    public static final String EMPTY_STRING = "";
    public static final String MIME_TYPE_IMAGE = "image";
    public static final String REGULAR_EXPRESSION_HEX_NUMBERS = "-?[0-9a-fA-F]+";
    public static final String PROPERTY_FILE_PATH = "./temp/";
    public static final String PROPERTY_DOWNLOAD_FILE_PATH = "./public/";
    public static final String CHECKSUM_NAME = "checksum";
    public static final String FILE_NAME = "file";
    public static final String JSON_TAG_EXTRACTED_MESSAGE = "ExtractedMessage";
    public static final String JSON_TAG_DOWNLOAD_URL = "DownloadURL";
    public static final String JSON_TAG_MESSAGE = "Message";
    public static final String JSON_TAG_TIMESTAMP = "Timestamp";
    public static final String LANGUAGE = "language";
    public static final String PDF_FILE_EXTENSION = "pdf";
    public static final String SPACE = " ";
    static final String MIME_TYPE_APPLICATION_PDF = "application/pdf";
    static final String MD5 = "MD5";
    static final String FORMAT = "%02x";
    static final int BYTE = 1024;
    public static final byte ZERO = 0;
    public static final byte MINUS_ONE = -1;
    public static final byte CHECKSUM_SIZE = 32;

    public static final String PARAM_FILE = "file";
    public static final String PARAM_CHECKSUM = "checksum";
    public static final String PARAM_START_PAGE = "startPage";
    public static final String PARAM_END_PAGE = "endPage";

    public static final String STATUS = "Status";

    public static final ArrayList<String> SUPPORTED_LANGUAGE = new ArrayList<String>(Arrays.asList("eng", "spa"));
}
