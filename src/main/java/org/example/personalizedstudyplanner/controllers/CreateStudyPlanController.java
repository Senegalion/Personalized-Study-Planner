package org.example.personalizedstudyplanner.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.exceptions.DatabaseException;
import org.example.personalizedstudyplanner.services.StudyPlanService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreateStudyPlanController {
    @FXML
    private Button languagePLButton;
    @FXML
    private Button languageENButton;
    @FXML
    private Button languageZHButton;
    @FXML
    private Button createPlannerButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label titleLabel;

    @FXML
    private TextField titleENField;
    @FXML
    private TextArea descriptionENField;

    @FXML
    private TextField titlePLField;
    @FXML
    private TextArea descriptionPLField;

    @FXML
    private TextField titleZHField;
    @FXML
    private TextArea descriptionZHField;

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
    public void handleChangeLanguageZH(ActionEvent actionEvent) {
        setLanguage(new Locale("zh", "CN"));
    }

    @FXML
    public void handleCreateStudyPlan(ActionEvent event) {
        String titleEN = titleENField.getText();
        String descEN = descriptionENField.getText();
        String titlePL = titlePLField.getText();
        String descPL = descriptionPLField.getText();
        String titleZH = titleZHField.getText();
        String descZH = descriptionZHField.getText();

        if (titleEN.isEmpty() || descEN.isEmpty() || titlePL.isEmpty() || descPL.isEmpty()) {
            showAlert(rb.getString("error.title"), rb.getString("error.emptyFields"), Alert.AlertType.ERROR);
            return;
        }

        try {
            studyPlanService.createStudyPlanWithTranslations(titleEN, descEN, titlePL, descPL, titleZH, descZH);
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
        changeScene(event);
    }

    private void changeScene(ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/personalizedstudyplanner/Dashboard.fxml"), rb);
            Parent root = loader.load();
            stage.setScene(new Scene(root, 800, 600));
        } catch (IOException e) {
            throw new DatabaseException("Invalid");
        }
    }

    public StudyPlanService getStudyPlanService() {
        return studyPlanService;
    }

    public void setStudyPlanService(StudyPlanService studyPlanService) {
        this.studyPlanService = studyPlanService;
    }
}
