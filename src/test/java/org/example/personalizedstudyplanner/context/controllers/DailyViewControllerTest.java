package org.example.personalizedstudyplanner.context.controllers;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.personalizedstudyplanner.controllers.DailyViewController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyViewControllerTest {
    private DailyViewController controller;

    @BeforeEach
    void setUp() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
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
        Platform.runLater(() -> {
            LocalDate testDate = LocalDate.of(2025, 2, 11);
            controller.setDate(testDate, 1);
            Label dateLabel = null;
            try {
                dateLabel = (Label) getPrivateField(controller);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertEquals("Events for 2025-02-11", dateLabel.getText());
        });
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private Object getPrivateField(Object object) throws Exception {
        Field field = object.getClass().getDeclaredField("dateLabel");
        field.setAccessible(true);
        return field.get(object);
    }
}
