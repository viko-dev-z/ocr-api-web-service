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

package com.jalasoft.webservice.database;

import java.sql.*;

/**
 * Executes queries to insert or retrieve data from database.
 *
 * @author Alex
 * @version 1.0
 */
public class DBQuery {
    private String sqlQuery;
    private Connection conn;

    /**
     * Constructor to instantiate the ConnectionDB singleton.
     */
    public DBQuery(){
        conn = ConnectionDB.getInstance().getConnection();
    }

    /**
     * Method that inserts a File record.
     */
    public void insert (String checksum, String filePath){
        sqlQuery = "Insert into "
                  + ConnectionDB.getInstance().TABLE_NAME
                  + "(checksum, path) values (?, ?)";
        try {
            PreparedStatement state = conn.prepareStatement(sqlQuery);
            state.setString(1, checksum);
            state.setString(2, filePath);
            state.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that iterates over the data table for a matching checksum
     * and returns the path.
     */
    public String getPath (String checksum) {
        sqlQuery = "Select path from "
                  + ConnectionDB.getInstance().TABLE_NAME
                  + " Where checksum = '" + checksum + "'";
        ResultSet resultSet;
        String path = null;
        try (Statement state = conn.createStatement()) {
            resultSet = state.executeQuery(sqlQuery);

            while (resultSet.next()) {
                path = resultSet.getString("path");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return path;
    }
}
