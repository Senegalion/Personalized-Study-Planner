package org.example.personalizedstudyplanner.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
    private static String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection connect() {
        try {
            if (URL == null || USER == null || PASSWORD == null) {
                System.err.println("ERROR: Database environment variables are not set!");
                System.err.println("DB_URL: " + URL);
                System.err.println("DB_USER: " + USER);
                System.err.println("DB_PASSWORD: " + PASSWORD);
                throw new RuntimeException("Database environment variables are missing");
            }

            System.out.println("Attempting to connect to DB...");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful!");
            return connection;
        } catch (Exception e) {
            System.err.println("ERROR: Connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}
