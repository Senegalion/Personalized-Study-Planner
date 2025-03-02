package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.services.StudyEventService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public class ProgressController {
    @FXML
    private VBox assignmentProgressContainer;
    @FXML
    private VBox examProgressContainer;

    private final StudyEventService studyEventService = new StudyEventService();
    private LocalDate selectedDate;
    private int studyPlanId;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            loadAssignmentProgress();
            loadExamProgress();
        });
    }

    public void setDate(LocalDate date, int studyPlanId) {
        this.selectedDate = date;
        this.studyPlanId = studyPlanId;
        loadAssignmentProgress();
        loadExamProgress();
    }

    private void loadAssignmentProgress() {
        assignmentProgressContainer.getChildren().clear();
        List<Assignment> assignments = studyEventService.getAssignmentsForDate(selectedDate);

        for (Assignment assignment : assignments) {
            double progress = assignment.getStatus().getProgressValue();
            ProgressBar progressBar = new ProgressBar(progress);
            Label label = new Label(assignment.getTitle() + " - " + (int) (progress * 100) + "% Completed");

            VBox entry = new VBox(label, progressBar);
            assignmentProgressContainer.getChildren().add(entry);
        }
    }

    private void loadExamProgress() {
        examProgressContainer.getChildren().clear();
        List<Exam> exams = studyEventService.getExamsForDate(selectedDate);

        for (Exam exam : exams) {
            double progress = calculateExamPreparationProgress(exam);
            ProgressBar progressBar = new ProgressBar(progress);
            Label label = new Label(exam.getSubject() + " - " + (int) (progress * 100) + "% Prepared");

            VBox entry = new VBox(label, progressBar);
            examProgressContainer.getChildren().add(entry);
        }
    }


    private double calculateExamPreparationProgress(Exam exam) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime examDate = exam.getDate();

        if (examDate.isBefore(now)) {
            return 1.0;
        }

        long totalDays = Duration.between(now, examDate).toDays();
        long daysElapsed = Math.max(1, totalDays - 7);

        return Math.min(1.0, (double) daysElapsed / totalDays);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

