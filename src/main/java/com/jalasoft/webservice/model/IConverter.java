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
import com.jalasoft.webservice.error_handler.ConvertException;

/**
 * Interface to be used by Image to Text and PDF to Text Converters.
 *
 * @author  Victor
 * @version 1.0
 */
public interface IConverter {

    // Abstract method which converts image text using a criteria.
    Response textExtractor(Criteria criteria) throws ConvertException;
}
