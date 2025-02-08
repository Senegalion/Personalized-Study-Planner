package org.example.personalizedstudyplanner.database;

import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUtilTest {

    @Test
    void testDatabaseConnection() {
        Connection connection = DatabaseUtil.connect();
        assertNotNull(connection, "Database connection should not be null");
    }
}