package org.example.personalizedstudyplanner.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class CalendarControllerTest {
    private CalendarController controller;

    @BeforeEach
    void setUp() {
        controller = new CalendarController();
    }

    @Test
    void testControllerInitialization() {
        assertNotNull(controller, "Controller should not be null");
    }

    @Test
    void testGetEventsForDayHandlesSQLException() {
        LocalDate testDate = LocalDate.now();
        assertDoesNotThrow(() -> controller.getEventsForDay(testDate), "Method should handle SQLException without throwing");
    }
}
