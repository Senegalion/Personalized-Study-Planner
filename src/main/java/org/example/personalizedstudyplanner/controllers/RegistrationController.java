package org.example.personalizedstudyplanner.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.config.database.DatabaseUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistrationController {

    public static final String REGISTER_NAME = "register.name";
    public static final String REGISTER_SURNAME = "register.surname";
    public static final String ERROR = "error";
    @FXML
    private TextField nameFieldEN;

    @FXML
    private TextField nameFieldPL;

    @FXML
    private TextField nameFieldZH;

    @FXML
    private TextField surnameFieldEN;

    @FXML
    private TextField surnameFieldPL;

    @FXML
    private TextField surnameFieldZH;

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
        setLanguage(Locale.getDefault());
    }

    private void setLanguage(Locale locale) {
        rb = ResourceBundle.getBundle("messages", locale);
        updateUI();
    }

    private void updateUI() {
        titleLabel.setText(rb.getString("register.title"));
        nameLabel.setText(rb.getString(REGISTER_NAME));
        surnameLabel.setText(rb.getString(REGISTER_SURNAME));
        peselLabel.setText(rb.getString("register.pesel"));
        emailLabel.setText(rb.getString("register.email"));
        passwordLabel.setText(rb.getString("register.password"));

        registerButton.setText(rb.getString("button.register"));
        loginButton.setText(rb.getString("button.backToLogin"));

        nameFieldEN.setPromptText(rb.getString(REGISTER_NAME) + " (EN)");
        nameFieldPL.setPromptText(rb.getString(REGISTER_NAME) + " (PL)");
        nameFieldZH.setPromptText(rb.getString(REGISTER_NAME) + " (ZH)");

        surnameFieldEN.setPromptText(rb.getString(REGISTER_SURNAME) + " (EN)");
        surnameFieldPL.setPromptText(rb.getString(REGISTER_SURNAME) + " (PL)");
        surnameFieldZH.setPromptText(rb.getString(REGISTER_SURNAME) + " (ZH)");

        peselField.setPromptText(rb.getString("register.pesel"));
        emailField.setPromptText(rb.getString("register.email"));
        passwordField.setPromptText(rb.getString("register.password"));
    }

    @FXML
    public void handleRegister(ActionEvent event) {
        String pesel = peselField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        String nameEN = nameFieldEN.getText();
        String namePL = nameFieldPL.getText();
        String nameZH = nameFieldZH.getText();

        String surnameEN = surnameFieldEN.getText();
        String surnamePL = surnameFieldPL.getText();
        String surnameZH = surnameFieldZH.getText();

        if (pesel.isEmpty() || email.isEmpty() || password.isEmpty() ||
                nameEN.isEmpty() || namePL.isEmpty() || nameZH.isEmpty() ||
                surnameEN.isEmpty() || surnamePL.isEmpty() || surnameZH.isEmpty()) {
            showAlert(rb.getString(ERROR), rb.getString("register.emptyFields"));
            return;
        }

        String studentSql = "INSERT INTO student (pesel, email, password) VALUES (?, ?, ?) RETURNING student_id";
        String translationSql = "INSERT INTO student_translation (student_id, language_code, name, surname) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.connect()) {
            assert conn != null;
            conn.setAutoCommit(false);

            int studentId;
            try (PreparedStatement stmt = conn.prepareStatement(studentSql)) {
                stmt.setString(1, pesel);
                stmt.setString(2, email);
                stmt.setString(3, password);

                var rs = stmt.executeQuery();
                if (rs.next()) {
                    studentId = rs.getInt("student_id");
                } else {
                    conn.rollback();
                    showAlert(rb.getString(ERROR), rb.getString("register.failure"));
                    return;
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(translationSql)) {
                stmt.setInt(1, studentId);
                stmt.setString(2, "en");
                stmt.setString(3, nameEN);
                stmt.setString(4, surnameEN);
                stmt.addBatch();

                stmt.setInt(1, studentId);
                stmt.setString(2, "pl");
                stmt.setString(3, namePL);
                stmt.setString(4, surnamePL);
                stmt.addBatch();

                stmt.setInt(1, studentId);
                stmt.setString(2, "zh");
                stmt.setString(3, nameZH);
                stmt.setString(4, surnameZH);
                stmt.addBatch();

                stmt.executeBatch();
            }

            conn.commit();
            showAlert(rb.getString("success"), rb.getString("register.success"));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(rb.getString(ERROR), rb.getString("register.databaseError"));
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
    public void goToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/personalizedstudyplanner/Login.fxml")));
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }

    public void changeLanguage(ActionEvent actionEvent) {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        switch (buttonText) {
            case "PL" -> setLanguage(new Locale("pl", "PL"));
            case "ZH" -> setLanguage(new Locale("zh", "CN"));
            default -> setLanguage(new Locale("en", "US"));
        }
    }
}
