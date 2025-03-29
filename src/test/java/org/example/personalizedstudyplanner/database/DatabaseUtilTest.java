package org.example.personalizedstudyplanner.database;

import org.example.personalizedstudyplanner.config.database.DatabaseUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUtilTest {

    @Test
    void testDatabaseConnection() {
        Connection connection = DatabaseUtil.connect();
        assertNotNull(connection, "Database connection should not be null");
    }

    @Test
    void testDatabaseConnectionFailure() {
        DatabaseUtil.setUrlForTesting("jdbc:postgresql://invalid-url:5432/personalized_study_planner");

        Connection connection = DatabaseUtil.connect();
        assertNull(connection, "Database connection should be null on failure");

        DatabaseUtil.setUrlForTesting("jdbc:postgresql://localhost:5432/personalized_study_planner");
    }
}
