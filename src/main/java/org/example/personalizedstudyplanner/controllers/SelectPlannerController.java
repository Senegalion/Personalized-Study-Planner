package org.example.personalizedstudyplanner.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.context.StudyPlanContext;
import org.example.personalizedstudyplanner.models.StudyPlan;
import org.example.personalizedstudyplanner.services.StudyPlanService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class SelectPlannerController {

    @FXML
    private ListView<StudyPlan> plannerListView;

    private final StudyPlanService studyPlanService = new StudyPlanService();
    private ObservableList<StudyPlan> observablePlanners;

    @FXML
    public void initialize() throws SQLException {
        List<StudyPlan> planners = studyPlanService.getAllStudyPlans();

        if (planners.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Planners Found");
            alert.setHeaderText(null);
            alert.setContentText("No existing planners found in the database.");
            alert.showAndWait();
        }

        observablePlanners = FXCollections.observableArrayList(planners);
        plannerListView.setItems(observablePlanners);
        plannerListView.setCellFactory(param -> new ListCell<StudyPlan>() {
            @Override
            protected void updateItem(StudyPlan item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitle() + " - " + item.getDescription());
                }
            }
        });
    }

    @FXML
    public void handleSelectPlanner(ActionEvent event) throws IOException {
        StudyPlan selectedPlanner = plannerListView.getSelectionModel().getSelectedItem();

        if (selectedPlanner != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Study Planner Selected");
            alert.setHeaderText(null);
            alert.setContentText("You selected: " + selectedPlanner);
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/personalizedstudyplanner/CalendarView.fxml"));
            Parent root = loader.load();

            StudyPlanContext.setCurrentStudyPlanId(selectedPlanner.getStudyPlanId());
            System.out.println("Study Plan ID 1: " + StudyPlanContext.getCurrentStudyPlanId());

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a planner.");
            alert.showAndWait();
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/personalizedstudyplanner/Dashboard.fxml")));
        stage.setScene(new Scene(root, 800, 600));
    }

    @FXML
    public void handleDeletePlanner(ActionEvent event) {
        StudyPlan selectedPlanner = plannerListView.getSelectionModel().getSelectedItem();

        if (selectedPlanner != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to delete '" + selectedPlanner.getTitle() + "'?");

            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        studyPlanService.deleteStudyPlan(selectedPlanner.getStudyPlanId());
                        observablePlanners.remove(selectedPlanner);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Deletion Error");
                        error.setHeaderText(null);
                        error.setContentText("Failed to delete the study planner.");
                        error.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a planner to delete.");
            alert.showAndWait();
        }
    }

}
