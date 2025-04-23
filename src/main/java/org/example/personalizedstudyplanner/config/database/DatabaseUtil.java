package org.example.personalizedstudyplanner.config.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
    private static String URL = "jdbc:postgresql://localhost:5432/personalized_study_planner";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setUrlForTesting(String url) {
        URL = url;
    }
}
