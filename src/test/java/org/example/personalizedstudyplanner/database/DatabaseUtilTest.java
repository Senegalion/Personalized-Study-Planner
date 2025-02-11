package org.example.personalizedstudyplanner.database;

import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUtilTest {

    @Test
    void testDatabaseConnection() {
        // Act
        Connection connection = DatabaseUtil.connect();

        // Assert
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