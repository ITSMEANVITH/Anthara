package com.eventbooking.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // Reads DB config from environment variables (set these on your host / Railway).
    // Falls back to local defaults so nothing breaks when running on your own machine.
    private static final String HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String PORT = System.getenv().getOrDefault("DB_PORT", "3306");
    private static final String DB_NAME = System.getenv().getOrDefault("DB_NAME", "event_ticket_booking");
    private static final String URL = System.getenv().getOrDefault(
            "DB_URL",
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useSSL=true&allowPublicKeyRetrieval=true"
    );
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "");

    public static Connection getConnection() {

        Connection con = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}