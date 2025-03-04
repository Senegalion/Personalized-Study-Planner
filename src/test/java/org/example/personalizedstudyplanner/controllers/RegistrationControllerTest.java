package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RegistrationControllerTest {
    private RegistrationController controller;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        JavaFXTestUtil.initJavaFX();

    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller = new RegistrationController();

            try {
                setPrivateField(controller, "nameField", new TextField());
                setPrivateField(controller, "surnameField", new TextField());
                setPrivateField(controller, "peselField", new TextField());
                setPrivateField(controller, "emailField", new TextField());
                setPrivateField(controller, "passwordField", new PasswordField());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testHandleRegister_ShouldNotThrowException() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            assertDoesNotThrow(() -> controller.handleRegister(null));
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }



    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

}