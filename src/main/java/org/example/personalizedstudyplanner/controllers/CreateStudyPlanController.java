package org.example.personalizedstudyplanner.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.services.StudyPlanService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreateStudyPlanController {
    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Button languagePLButton, languageENButton, createPlannerButton, cancelButton;

    @FXML
    private Label titleLabel;

    private StudyPlanService studyPlanService;

    private ResourceBundle rb;
    private Locale currentLocale = Locale.getDefault();

    public CreateStudyPlanController() {
        this.studyPlanService = new StudyPlanService();
    }

    @FXML
    public void initialize() {
        setLanguage(currentLocale);
    }

    private void setLanguage(Locale locale) {
        rb = ResourceBundle.getBundle("messages", locale);
        updateUI();
    }

    private void updateUI() {
        titleLabel.setText(rb.getString("createPlanner.title"));
        titleField.setPromptText(rb.getString("createPlanner.enterTitle"));
        descriptionField.setPromptText(rb.getString("createPlanner.enterDescription"));
        createPlannerButton.setText(rb.getString("button.createPlanner"));
        cancelButton.setText(rb.getString("button.cancel"));
    }

    @FXML
    private void handleChangeLanguagePL(ActionEvent event) {
        setLanguage(new Locale("pl", "PL"));
    }

    @FXML
    private void handleChangeLanguageEN(ActionEvent event) {
        setLanguage(new Locale("en", "US"));
    }

    @FXML
    public void handleCreateStudyPlan(ActionEvent event) {
        String title = titleField.getText();
        String description = descriptionField.getText();

        if (title.isEmpty() || description.isEmpty()) {
            showAlert(rb.getString("error.title"), rb.getString("error.emptyFields"), Alert.AlertType.ERROR);
            return;
        }

        try {
            studyPlanService.createStudyPlan(title, description);
            showAlert(rb.getString("success.title"), rb.getString("success.studyPlanCreated"), Alert.AlertType.INFORMATION);
            goBack(event);
        } catch (SQLException e) {
            showAlert(rb.getString("error.title"), rb.getString("error.database"), Alert.AlertType.ERROR);
        }
    }

    public void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goBack(ActionEvent event) {
        changeScene(event, "/org/example/personalizedstudyplanner/Dashboard.fxml");
    }

    private void changeScene(ActionEvent event, String fxmlPath) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath), rb);
            Parent root = loader.load();
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TextField getTitleField() {
        return titleField;
    }

    public void setTitleField(TextField titleField) {
        this.titleField = titleField;
    }

    public TextArea getDescriptionField() {
        return descriptionField;
    }

    public void setDescriptionField(TextArea descriptionField) {
        this.descriptionField = descriptionField;
    }

    public StudyPlanService getStudyPlanService() {
        return studyPlanService;
    }

    public void setStudyPlanService(StudyPlanService studyPlanService) {
        this.studyPlanService = studyPlanService;
    }
}
