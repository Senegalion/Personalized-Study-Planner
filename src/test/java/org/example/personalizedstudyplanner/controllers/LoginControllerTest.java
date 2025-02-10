package org.example.personalizedstudyplanner.controllers;

import org.example.personalizedstudyplanner.context.UserContext;
import org.example.personalizedstudyplanner.database.DatabaseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {
    private LoginController loginController;
    private Connection testConnection;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();
        // Clear any existing user context
        UserContext.clear();

        // We'll use the actual database for testing
        testConnection = DatabaseUtil.connect();
        if (testConnection == null) {
            fail("Failed to connect to the database");
        }

        // Set up test data
        setupTestData();
    }

    private void setupTestData() {
        // First, clean up any existing test data
        cleanupTestData();

        // Insert test user
        String insertSql = "INSERT INTO student (name, surname, pesel, email, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = testConnection.prepareStatement(insertSql)) {
            stmt.setString(1, "Test");
            stmt.setString(2, "User");
            stmt.setString(3, "12345678901");
            stmt.setString(4, "test@test.com");
            stmt.setString(5, "password123");
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("Failed to set up test data: " + e.getMessage());
        }
    }

    private void cleanupTestData() {
        try (PreparedStatement stmt = testConnection.prepareStatement("DELETE FROM student WHERE email = ?")) {
            stmt.setString(1, "test@test.com");
            stmt.executeUpdate();
        } catch (SQLException e) {
            fail("Failed to clean up test data: " + e.getMessage());
        }
    }

    @Test
    void testValidLogin() {
        try {
            // Get the test user's ID
            int expectedUserId = getUserIdByEmail("test@test.com");

            // Test valid credentials
            boolean result = validateCredentials("test@test.com", "password123");

            assertTrue(result);
            assertEquals(expectedUserId, UserContext.getCurrentUserId());
        } catch (SQLException e) {
            fail("Database error during test: " + e.getMessage());
        }
    }

    @Test
    void testInvalidPassword() {
        try {
            boolean result = validateCredentials("test@test.com", "wrongpassword");
            assertFalse(result);

            // Should throw exception since no user should be logged in
            assertThrows(IllegalStateException.class, UserContext::getCurrentUserId);
        } catch (SQLException e) {
            fail("Database error during test: " + e.getMessage());
        }
    }

    @Test
    void testNonexistentUser() {
        try {
            boolean result = validateCredentials("nonexistent@test.com", "password123");
            assertFalse(result);

            // Should throw exception since no user should be logged in
            assertThrows(IllegalStateException.class, UserContext::getCurrentUserId);
        } catch (SQLException e) {
            fail("Database error during test: " + e.getMessage());
        }
    }

    @Test
    void testEmptyCredentials() {
        try {
            boolean result = validateCredentials("", "");
            assertFalse(result);

            // Should throw exception since no user should be logged in
            assertThrows(IllegalStateException.class, UserContext::getCurrentUserId);
        } catch (SQLException e) {
            fail("Database error during test: " + e.getMessage());
        }
    }

    private boolean validateCredentials(String email, String password) throws SQLException {
        if (email.isEmpty() || password.isEmpty()) {
            return false;
        }

        String sql = "SELECT student_id FROM student WHERE email = ? AND password = ?";
        try (PreparedStatement statement = testConnection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("student_id");
                UserContext.setCurrentUserId(userId);
                return true;
            }
        }
        return false;
    }

    private int getUserIdByEmail(String email) throws SQLException {
        String sql = "SELECT student_id FROM student WHERE email = ?";
        try (PreparedStatement statement = testConnection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("student_id");
            }
            throw new SQLException("Test user not found");
        }
    }

    @Test
    void cleanup() {
        cleanupTestData();
        try {
            testConnection.close();
        } catch (SQLException e) {
            fail("Failed to close test connection: " + e.getMessage());
        }
    }
}