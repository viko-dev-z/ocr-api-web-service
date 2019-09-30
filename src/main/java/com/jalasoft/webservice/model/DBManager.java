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

import com.jalasoft.webservice.database.DBQuery;

/**
 * Implements class to manage queries
 *
 * @author  Alex
 * @version 1.0
 */
public class DBManager {

    // Object instance that manages insert and select DB queries for Files.
    DBQuery dbQuery;

     public DBManager() {
         dbQuery = new DBQuery();
     }

     public void addFile(String checksum, String path){
         dbQuery.insert(checksum, path);
     }

     public String getPath(String checksum){
         return  dbQuery.getPath(checksum);
     }
}
