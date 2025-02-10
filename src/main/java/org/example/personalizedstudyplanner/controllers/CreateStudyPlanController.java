package org.example.personalizedstudyplanner.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.services.StudyPlanService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class CreateStudyPlanController {
    @FXML
    TextField titleField;

    @FXML
    TextArea descriptionField;

    StudyPlanService studyPlanService;

    public CreateStudyPlanController() {
        this.studyPlanService = new StudyPlanService();
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void handleCreateStudyPlan(ActionEvent event) {
        String title = titleField.getText();
        String description = descriptionField.getText();

        if (title.isEmpty() || description.isEmpty()) {
            showAlert("Input Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        try {
            studyPlanService.createStudyPlan(title, description);

            showAlert("Study Plan Created", "Your study plan has been created successfully!", Alert.AlertType.INFORMATION);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/personalizedstudyplanner/Dashboard.fxml")));
            stage.setScene(new Scene(root, 800, 600));

        } catch (SQLException | IOException e) {
            showAlert("Database Error", "There was an error saving your study plan.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/personalizedstudyplanner/Dashboard.fxml")));
        stage.setScene(new Scene(root, 800, 600));
    }
}
