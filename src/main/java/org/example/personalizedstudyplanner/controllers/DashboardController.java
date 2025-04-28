package org.example.personalizedstudyplanner.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardController {
    @FXML
    public Button languagePLButton;
    @FXML
    public Button languageENButton;
    @FXML
    public Button languageZHButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Button createPlannerButton;

    @FXML
    private Button selectPlannerButton;

    @FXML
    private Button logoutButton;

    private ResourceBundle rb;
    private final Locale currentLocale = Locale.getDefault();

    public void initialize() {
        setLanguage(currentLocale);
    }

    private void setLanguage(Locale locale) {
        rb = ResourceBundle.getBundle("messages", locale);
        updateUI();
    }

    private void updateUI() {
        titleLabel.setText(rb.getString("welcomeTitle"));
        createPlannerButton.setText(rb.getString("button.createPlanner"));
        selectPlannerButton.setText(rb.getString("button.viewPlanners"));
        logoutButton.setText(rb.getString("button.logout"));
    }

    @FXML
    public void handleCreatePlanner(ActionEvent event) throws IOException {
        changeScene(event, "/org/example/personalizedstudyplanner/CreatePlanner.fxml");
    }

    @FXML
    public void handleSelectPlanner(ActionEvent event) throws IOException {
        changeScene(event, "/org/example/personalizedstudyplanner/SelectPlanner.fxml");
    }

    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        changeScene(event, "/org/example/personalizedstudyplanner/Login.fxml");
    }

    @FXML
    public void handleChangeLanguagePL(ActionEvent event) {
        setLanguage(new Locale("pl", "PL"));
    }

    @FXML
    public void handleChangeLanguageEN(ActionEvent event) {
        setLanguage(new Locale("en", "US"));
    }

    @FXML
    public void handleChangeLanguageZH(ActionEvent event) {
        setLanguage(new Locale("zh", "CN"));
    }

    private void changeScene(ActionEvent event, String fxmlPath) throws IOException {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath), rb);
        Parent root = loader.load();
        stage.setScene(new Scene(root, 800, 600));
    }
}
