package com.family.server.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    private static final Dotenv dotenv;

    static {
        // 1. Load .env
        Dotenv tmp = null;
        try {
            tmp = Dotenv.load();
            System.out.println("[JDBC] .env loaded");
        } catch (Exception e) {
            System.err.println("[JDBC] Cannot load .env: " + e.getMessage());
        }
        dotenv = tmp;

        // 2. Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[JDBC] MySQL Driver loaded OK");
        } catch (ClassNotFoundException e) {
            System.err.println("[JDBC] Cannot load MySQL driver: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            if (dotenv == null) {
                System.err.println("[JDBC] Dotenv is null → cannot read DB config");
                return null;
            }

            String url = dotenv.get("DB_URL");
            String userName = dotenv.get("DB_USERNAME");
            String password = dotenv.get("DB_PASSWORD");

            System.out.println("[JDBC] Try connect: " + url + " user=" + userName);

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
