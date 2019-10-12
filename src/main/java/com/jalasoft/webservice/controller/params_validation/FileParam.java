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

package com.jalasoft.webservice.controller.params_validation;

import com.jalasoft.webservice.error_handler.ParamsInvalidException;
import org.springframework.web.multipart.MultipartFile;

public class FileParam extends AbstractParam {
    String inputChecksum;

    public FileParam(String name, MultipartFile value, String inputChecksum){
        setName(name);
        setValue(value);
        this.inputChecksum = inputChecksum;
    }

    public void accept(Visitor visitor) throws ParamsInvalidException {
        visitor.visit(this);
    }

    public String getInputChecksum() {
        return this.inputChecksum;
    }
}
