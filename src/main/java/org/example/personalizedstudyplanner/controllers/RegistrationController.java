package org.example.personalizedstudyplanner.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class RegistrationController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleRegister(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        System.out.println("Registered: " + email);
    }

    @FXML
    public void goToLogin(ActionEvent event) throws Exception {
        Stage stage = (Stage) emailField.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/personalizedstudyplanner/Login.fxml")));
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }
}
