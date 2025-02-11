package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DashboardControllerTest {
    private DashboardController controller;
    private ActionEvent mockEvent;

    private static boolean initialized = false;
    @BeforeAll
    static void initToolkit() throws InterruptedException {
        if (!initialized) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            latch.await(5, TimeUnit.SECONDS);
            initialized = true;
        }
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {
            controller = new DashboardController();
            Button button = new Button();
            Stage stage = new Stage();
            Scene scene = new Scene(button);
            stage.setScene(scene);
            stage.show();
            mockEvent = new ActionEvent(button, null); // Simulated event
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @AfterAll
    static void closeToolkit() {
        if (initialized) {
            Platform.exit(); // Close JavaFX Application Thread
        }
    }



    @Test
    void testHandleSelectPlanner_ShouldNotThrowException() {
        Platform.runLater(() -> assertDoesNotThrow(() -> controller.handleSelectPlanner(mockEvent)));
    }
}