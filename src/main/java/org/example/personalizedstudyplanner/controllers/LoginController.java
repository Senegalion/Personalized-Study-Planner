package org.example.personalizedstudyplanner.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.config.database.DatabaseUtil;
import org.example.personalizedstudyplanner.context.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    public Button loginButton;
    @FXML
    public Button registerButton;
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private ResourceBundle rb;

    public void initialize() {
        setLanguage(Locale.getDefault());
    }

    private void setLanguage(Locale locale) {
        rb = ResourceBundle.getBundle("messages", locale);
        updateUI();
    }

    private void updateUI() {
        emailField.setPromptText(rb.getString("login.email"));
        passwordField.setPromptText(rb.getString("login.password"));

        loginButton.setText(rb.getString("button.login"));
        registerButton.setText(rb.getString("button.register"));
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(rb.getString("error"), rb.getString("login.emptyFields"));
            return;
        }

        String sql = "SELECT * FROM student WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("student_id");
                UserContext.setCurrentUserId(userId);
                Stage stage = (Stage) emailField.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/personalizedstudyplanner/Dashboard.fxml")));
                stage.setScene(new Scene(root, 800, 600));
            } else {
                showAlert(rb.getString("error"), rb.getString("login.invalidCredentials"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(rb.getString("error"), rb.getString("login.databaseError"));
        }
    }

    @FXML
    public void goToRegistration(ActionEvent event) throws Exception {
        Stage stage = (Stage) emailField.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/personalizedstudyplanner/Registration.fxml")));
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void changeLanguage(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        if (buttonText.equals("EN")) {
            setLanguage(new Locale("en", "US"));
        } else if (buttonText.equals("PL")) {
            setLanguage(new Locale("pl", "PL"));
        }
    }
}
