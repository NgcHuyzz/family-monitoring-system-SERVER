package com.family.server.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static final Dotenv dotenv = Dotenv.load();

    public static Connection getConnection() {
        try {
            String url = dotenv.get("DB_URL");
            String userName = dotenv.get("DB_USERNAME");
            String password = dotenv.get("DB_PASSWORD");

            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.err.println("database connection fail: " + e.getMessage());
            return null;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
