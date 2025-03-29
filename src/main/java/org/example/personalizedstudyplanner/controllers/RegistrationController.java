package org.example.personalizedstudyplanner.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.config.database.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class RegistrationController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField peselField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleRegister(ActionEvent event) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String pesel = peselField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || surname.isEmpty() || pesel.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "All fields must be filled!");
            return;
        }

        String sql = "INSERT INTO student (name, surname, pesel, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, pesel);
            stmt.setString(4, email);
            stmt.setString(5, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                showAlert("Success", "Registration successful!");
            } else {
                showAlert("Error", "Registration failed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Database error!");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goToLogin(ActionEvent event) throws Exception {
        Stage stage = (Stage) emailField.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/personalizedstudyplanner/Login.fxml")));
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }
}
