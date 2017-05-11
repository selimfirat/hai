package com.hai.gui.data;

import java.sql.*;

/**
 * Created by mrsfy on 09-May-17.
 */
public class DB {
    private static Connection connection;

    public static void initConnection(String dbURL) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            System.out.println("Connected to mssql");
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static PreparedStatement prepareStatement(String query) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stmt;
    }


}
