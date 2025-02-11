package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.personalizedstudyplanner.database.DatabaseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class RegistrationControllerTest extends ApplicationTest {

    private TextField nameField;
    private TextField surnameField;
    private TextField peselField;
    private TextField emailField;
    private PasswordField passwordField;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @InjectMocks
    private RegistrationController registrationController;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        nameField = new TextField();
        surnameField = new TextField();
        peselField = new TextField();
        emailField = new TextField();
        passwordField = new PasswordField();
        registrationController.nameField = nameField;
        registrationController.surnameField = surnameField;
        registrationController.peselField = peselField;
        registrationController.emailField = emailField;
        registrationController.passwordField = passwordField;

        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::connect).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        }
    }

    @Test
    public void handleRegisterShowsErrorWhenFieldsAreEmpty() throws SQLException {
        Platform.runLater(() -> {
            nameField.setText("");
            surnameField.setText("");
            peselField.setText("");
            emailField.setText("");
            passwordField.setText("");

            registrationController.handleRegister(new ActionEvent());

            try {
                verify(preparedStatement, never()).executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


}