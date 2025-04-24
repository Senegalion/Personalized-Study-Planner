package org.example.personalizedstudyplanner.config.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
    private static String url = "jdbc:postgresql://localhost:5432/personalized_study_planner";
    private static final String USER = "postgres";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    private DatabaseUtil() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    public static Connection connect() {
        try {
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Error while connecting to the database");
            return null;
        }
    }

    public static void setUrlForTesting(String url) {
        DatabaseUtil.url = url;
    }
}
