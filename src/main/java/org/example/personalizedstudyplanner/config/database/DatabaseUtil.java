package org.example.personalizedstudyplanner.config.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);
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
            logger.error("Error while connecting to the database");
            return null;
        }
    }

    public static void setUrlForTesting(String url) {
        DatabaseUtil.url = url;
    }
}
