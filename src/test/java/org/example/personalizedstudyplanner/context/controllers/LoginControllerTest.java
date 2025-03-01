package org.example.personalizedstudyplanner.context.controllers;

import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.personalizedstudyplanner.controllers.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LoginControllerTest {
    private LoginController controller;

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            controller = new LoginController();
            try {
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
    void testHandleLogin_EmptyFields_ShouldNotThrowException() {
        Platform.runLater(() -> {
            try {
                TextField emailField = (TextField) getPrivateField(controller, "emailField");
                PasswordField passwordField = (PasswordField) getPrivateField(controller, "passwordField");
                emailField.setText("");
                passwordField.setText("");
                assertDoesNotThrow(() -> controller.handleLogin(null));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
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
