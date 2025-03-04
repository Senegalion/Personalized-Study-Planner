package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.database.DatabaseUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.Connection;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class LoginControllerTest {
    private LoginController controller;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        JavaFXTestUtil.initJavaFX();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
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
    void testHandleLogin_EmptyFields_ShouldNotThrowException() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

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
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }



    @Test
    void testGoToRegistration_ShouldNavigateToRegistration() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage mockStage = mock(Stage.class);
                Scene mockScene = new Scene(new Parent() {});
                when(mockStage.getScene()).thenReturn(mockScene);
                TextField emailField = (TextField) getPrivateField(controller, "emailField");
                setPrivateField(emailField, "scene", mockScene);

                assertDoesNotThrow(() -> controller.goToRegistration(mock(ActionEvent.class)));

                verify(mockStage).setScene(any(Scene.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

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