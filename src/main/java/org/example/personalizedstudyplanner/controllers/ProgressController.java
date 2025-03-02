package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import org.example.personalizedstudyplanner.context.StudyPlanContext;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.services.StudyEventService;

import java.sql.SQLException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public class ProgressController {
    @FXML
    private VBox assignmentProgressContainer;
    @FXML
    private VBox examProgressContainer;

    private final StudyEventService studyEventService = new StudyEventService();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            try {
                loadAssignmentProgress();
                loadExamProgress();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadAssignmentProgress() throws SQLException {
        int studyPlanId = StudyPlanContext.getCurrentStudyPlanId();
        List<Assignment> assignments = studyEventService.getAllAssignments(studyPlanId);
        assignmentProgressContainer.getChildren().clear();

        for (Assignment assignment : assignments) {
            double progress = assignment.getStatus().getProgressValue();
            ProgressBar progressBar = new ProgressBar(progress);
            Label label = new Label(assignment.getTitle() + " - " + (int) (progress * 100) + "% Completed");

            VBox entry = new VBox(label, progressBar);
            assignmentProgressContainer.getChildren().add(entry);
        }
    }

    private void loadExamProgress() throws SQLException {
        int studyPlanId = StudyPlanContext.getCurrentStudyPlanId();
        List<Exam> exams = studyEventService.getAllExams(studyPlanId);
        examProgressContainer.getChildren().clear();

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
}

