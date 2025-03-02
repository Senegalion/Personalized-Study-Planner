package org.example.personalizedstudyplanner.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.services.StudyEventService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class DailyViewController {
    @FXML
    private Label dateLabel;
    @FXML
    private ListView<String> eventsListView;
    @FXML
    private Button addEventButton;

    private final StudyEventService studyEventService = new StudyEventService();
    private LocalDate selectedDate;
    private int studyPlanId;

    public void setDate(LocalDate date, int studyPlanId) {
        this.selectedDate = date;
        this.studyPlanId = studyPlanId;
        dateLabel.setText("Events for " + selectedDate);
        loadEvents();
    }

    private void loadEvents() {
        eventsListView.getItems().clear();
        List<Assignment> assignments = studyEventService.getAssignmentsForDate(selectedDate);
        List<Exam> exams = studyEventService.getExamsForDate(selectedDate);
        List<ClassSchedule> classes = studyEventService.getClassesForDate(selectedDate);

        for (Assignment a : assignments) eventsListView.getItems().add("📌 Assignment: " + a.getTitle());
        for (Exam e : exams) eventsListView.getItems().add("📝 Exam: " + e.getSubject());
        for (ClassSchedule c : classes) eventsListView.getItems().add("📚 Class: " + c.getClassName());

        if (eventsListView.getItems().isEmpty()) {
            eventsListView.getItems().add("No events planned.");
        }
    }

    @FXML
    private void handleAddEvent() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Assignment", "Assignment", "Exam", "Class Schedule");
        dialog.setTitle("Select Event Type");
        dialog.setHeaderText("Choose the type of event to add:");
        dialog.setContentText("Event Type:");

        dialog.showAndWait().ifPresent(eventType -> {
            CalendarController calendarController = new CalendarController();
            calendarController.openEventForm(selectedDate, eventType);
            loadEvents();
        });
    }


    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleViewProgress(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/personalizedstudyplanner/ProgressView.fxml"));
            Parent root = loader.load();

            ProgressController progressController = loader.getController();
            progressController.setDate(selectedDate, studyPlanId);

            Stage stage = new Stage();
            stage.setTitle("Study Progress");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
