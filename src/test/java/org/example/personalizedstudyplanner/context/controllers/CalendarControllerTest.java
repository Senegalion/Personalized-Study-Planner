package org.example.personalizedstudyplanner.context.controllers;

import org.example.personalizedstudyplanner.controllers.CalendarController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

//    @Test
//    void testGetEventsForDayHandlesSQLException() {
//        LocalDate testDate = LocalDate.now();
//        assertDoesNotThrow(() -> controller.getEventsForDay(testDate), "Method should handle SQLException without throwing");
//    }
}
