package org.example.personalizedstudyplanner.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.config.database.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

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
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label peselLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

    private ResourceBundle rb;

    public void initialize() {
        setLanguage(Locale.getDefault());  // Domyślny język to systemowy
    }

    private void setLanguage(Locale locale) {
        rb = ResourceBundle.getBundle("messages", locale);  // Ładowanie odpowiednich zasobów
        updateUI();
    }

    private void updateUI() {
        // Ustawianie tekstów dla elementów interfejsu
        titleLabel.setText(rb.getString("register.title"));
        nameLabel.setText(rb.getString("register.name"));
        surnameLabel.setText(rb.getString("register.surname"));
        peselLabel.setText(rb.getString("register.pesel"));
        emailLabel.setText(rb.getString("register.email"));
        passwordLabel.setText(rb.getString("register.password"));

        registerButton.setText(rb.getString("button.register"));
        loginButton.setText(rb.getString("button.backToLogin"));

        // Ustawienie prompt text dla TextField
        nameField.setPromptText(rb.getString("register.name"));
        surnameField.setPromptText(rb.getString("register.surname"));
        peselField.setPromptText(rb.getString("register.pesel"));
        emailField.setPromptText(rb.getString("register.email"));
        passwordField.setPromptText(rb.getString("register.password"));
    }

    @FXML
    public void handleRegister(ActionEvent event) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String pesel = peselField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || surname.isEmpty() || pesel.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(rb.getString("error"), rb.getString("register.emptyFields"));
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
                showAlert(rb.getString("success"), rb.getString("register.success"));
            } else {
                showAlert(rb.getString("error"), rb.getString("register.failure"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(rb.getString("error"), rb.getString("register.databaseError"));
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

    public void changeLanguage(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        if (buttonText.equals("EN")) {
            setLanguage(new Locale("en", "US"));
        } else if (buttonText.equals("PL")) {
            setLanguage(new Locale("pl", "PL"));
        }
    }
}
