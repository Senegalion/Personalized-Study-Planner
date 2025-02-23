package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyViewControllerTest {
    private DailyViewController controller;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        JavaFXTestUtil.initJavaFX();
    }

    @BeforeEach
    void setUp() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller = new DailyViewController();

            try {
                setPrivateField(controller, "dateLabel", new Label());
                setPrivateField(controller, "eventsListView", new ListView<>());
                setPrivateField(controller, "addEventButton", new Button());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testSetDate_ShouldUpdateLabel() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            LocalDate testDate = LocalDate.of(2025, 2, 11);
            controller.setDate(testDate, 1);

            Label dateLabel = null;
            try {
                dateLabel = (Label) getPrivateField(controller, "dateLabel");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertEquals("Events for 2025-02-11", dateLabel.getText());
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private Object getPrivateField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}
