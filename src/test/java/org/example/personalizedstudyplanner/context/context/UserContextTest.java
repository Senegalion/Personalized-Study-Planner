package org.example.personalizedstudyplanner.context.context;

import org.example.personalizedstudyplanner.context.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserContextTest {
    @BeforeEach
    void setUp() {
        UserContext.clear();
    }

    @Test
    void testSetAndGetCurrentUserId() {
        UserContext.setCurrentUserId(1);
        assertEquals(1, UserContext.getCurrentUserId());
    }

    @Test
    void testGetCurrentUserIdWhenNotSet() {
        Exception exception = assertThrows(IllegalStateException.class, UserContext::getCurrentUserId);
        assertEquals("No user is currently logged in.", exception.getMessage());
    }

    @Test
    void testClear() {
        UserContext.setCurrentUserId(1);
        UserContext.clear();
        Exception exception = assertThrows(IllegalStateException.class, UserContext::getCurrentUserId);
        assertEquals("No user is currently logged in.", exception.getMessage());
    }
}