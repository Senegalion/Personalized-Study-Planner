package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SelectPlannerControllerTest {
    private SelectPlannerController controller;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        JavaFXTestUtil.initJavaFX(); // Centralized initialization
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller = new SelectPlannerController();
            try {
                setPrivateField(controller, "plannerListView", new ListView<>());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testHandleSelectPlanner_WithNoSelection_ShouldNotThrowException() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> controller.handleSelectPlanner(null));
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS); // Ensure test waits for assertion
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    @AfterAll
    static void tearDown() {
        JavaFXTestUtil.shutdownJavaFX(); // Centralized shutdown
    }
}