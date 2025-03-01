package org.example.personalizedstudyplanner.context.database;

import org.example.personalizedstudyplanner.database.DatabaseUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUtilTest {

    @Test
    void testDatabaseConnection() {
        Connection connection = DatabaseUtil.connect();

        try {
            assertNotNull(connection, "Database connection should not be null");
            assertFalse(connection.isClosed(), "Connection should be open");
            assertTrue(connection.isValid(5), "Connection should be valid");
            assertEquals("PostgreSQL", connection.getMetaData().getDatabaseProductName(),
                    "Should be connected to PostgreSQL database");
        } catch (Exception e) {
            fail("Database connection test failed: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                fail("Failed to close database connection: " + e.getMessage());
            }
        }
    }
}
