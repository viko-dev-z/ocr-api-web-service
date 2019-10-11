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

/**
 * Singleton implementation to establish Database Connection only once.
 *
 * @author Alex
 * @version 1.0
 */
public class ConnectionDB {

    private static ConnectionDB instance;
    private static Connection conn;
    // Constant to assign table name
    static final String TABLE_NAME = "fileState";
    static final String USER_TABLE_NAME = "userTable";

    /**
     * Private constructor to prevent direct instantiation.
     */
    private ConnectionDB() {
        this.init();
    }

    /**
     * public method that ensures only one instance is returned.
     */
    public static ConnectionDB getInstance()  {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    /**
     * private method which establish a database connection.
     */
    private void init()  {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:webService.db");
            Statement state = conn.createStatement();
            state.execute("create table if not exists "
                    + TABLE_NAME
                    + "( Id integer primary key, " +
                      "Checksum varchar(32), " +
                      "Path varchar (250) );");
            state = conn.createStatement();
            state.execute("create table if not exists " +
                     USER_TABLE_NAME +
                     "( Id integer primary key, "+
                     "user varchar(32) UNIQUE, " +
                     "password varchar(8), " +
                     "role varchar(16), " +
                     "email varchar(16) UNIQUE );");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * public method that returns a Connection instance.
     */
    public Connection getConnection() {
        return conn;
    }

}
