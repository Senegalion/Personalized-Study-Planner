package org.example.personalizedstudyplanner.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardControllerTest {
    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        dashboardController = new DashboardController();
    }

    @Test
    void handleCreatePlanner() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            dashboardController.handleCreatePlanner(null);
        });
        assertNotNull(exception);
    }

    @Test
    void handleSelectPlanner() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            dashboardController.handleSelectPlanner(null);
        });
        assertNotNull(exception);
    }

    @Test
    void handleLogout() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            dashboardController.handleLogout(null);
        });
        assertNotNull(exception);
    }
}