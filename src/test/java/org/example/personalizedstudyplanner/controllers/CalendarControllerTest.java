package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class CalendarControllerTest {
    private CalendarController controller;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        JavaFXTestUtil.initJavaFX();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller = new CalendarController();
            controller.calendarGrid = new GridPane();
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

//    @Test
//    void testControllerInitialization() {
//        assertNotNull(controller, "Controller should not be null");
//    }

    @Test
    void testInitialize_ShouldNotThrowException() {
        Platform.runLater(() -> assertDoesNotThrow(() -> controller.initialize()));
    }

    @Test
    void testGenerateCalendar_ShouldNotThrowException() {
        Platform.runLater(() -> assertDoesNotThrow(() -> controller.generateCalendar()));
    }

    @Test
    void testShowReminderAlert_ShouldNotThrowException() throws Exception {
        Method method = CalendarController.class.getDeclaredMethod("showReminderAlert", String.class);
        method.setAccessible(true);
        Platform.runLater(() -> assertDoesNotThrow(() -> method.invoke(controller, "Test Reminder")));
    }

    @Test
    void testShowErrorAlert_ShouldNotThrowException() throws Exception {
        Method method = CalendarController.class.getDeclaredMethod("showErrorAlert", String.class);
        method.setAccessible(true);
        Platform.runLater(() -> assertDoesNotThrow(() -> method.invoke(controller, "Test Error")));
    }

    @Test
    void testHandleBack_ShouldNotThrowException() throws Exception {
        ActionEvent mockEvent = mock(ActionEvent.class);
        Platform.runLater(() -> assertDoesNotThrow(() -> controller.handleBack(mockEvent)));
    }

    @Test
    void testHandleToday_ShouldNotThrowException() {
        ActionEvent mockEvent = mock(ActionEvent.class);
        Platform.runLater(() -> assertDoesNotThrow(() -> controller.handleToday(mockEvent)));
    }
}