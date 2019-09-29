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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {

    private static ConnectionDB instance;
    private static Connection conn;
    private static String tableName;

    private ConnectionDB() {
        tableName = "fileState";
        this.init(tableName);
    }

    public static ConnectionDB getInstance()  {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    private void init(String tableName)  {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:webService.db");
            Statement state = conn.createStatement();
            state.execute("create table if not exists "
                    + tableName
                    + "( Id integer primary key, " +
                      "Checksum varchar(32), " +
                      "Path varchar (250) );");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public String getTableName(){
        return tableName;
    }
}
