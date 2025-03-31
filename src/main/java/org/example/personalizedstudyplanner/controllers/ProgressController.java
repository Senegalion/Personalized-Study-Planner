package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.AssignmentStatus;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.services.StudyEventService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProgressController {
    @FXML
    private VBox assignmentProgressContainer;
    @FXML
    private VBox examProgressContainer;
    @FXML
    private Label titleLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button languagePolishButton;
    @FXML
    private Button languageEnglishButton;

    private final StudyEventService studyEventService = new StudyEventService();
    private LocalDate selectedDate;
    private int studyPlanId;
    private Locale currentLocale;
    private ResourceBundle rb;

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

    private void setLanguage(Locale locale) {
        this.currentLocale = locale;
        rb = ResourceBundle.getBundle("messages", locale);
        updateUI();
    }

    private void updateUI() {
        titleLabel.setText(rb.getString("progressView.title"));
        backButton.setText(rb.getString("goBack"));
        languagePolishButton.setText(rb.getString("language.polish"));
        languageEnglishButton.setText(rb.getString("language.english"));
    }

    protected void loadAssignmentProgress() {
        assignmentProgressContainer.getChildren().clear();
        List<Assignment> assignments = studyEventService.getAssignmentsForDate(selectedDate);

        for (Assignment assignment : assignments) {
            double progress = assignment.getStatus().getProgressValue();
            ProgressBar progressBar = new ProgressBar(progress);
            Label label = new Label(assignment.getTitle() + " - " + (int) (progress * 100) + "% Completed");

            ComboBox<AssignmentStatus> statusComboBox = new ComboBox<>();
            statusComboBox.getItems().setAll(AssignmentStatus.values());
            statusComboBox.setValue(assignment.getStatus());

            statusComboBox.setOnAction(event -> {
                AssignmentStatus newStatus = statusComboBox.getValue();
                assignment.setStatus(newStatus);
                studyEventService.updateAssignmentStatus(assignment);
                loadAssignmentProgress();
            });

            VBox entry = new VBox(label, progressBar, statusComboBox);
            assignmentProgressContainer.getChildren().add(entry);
        }
    }

    protected void loadExamProgress() {
        examProgressContainer.getChildren().clear();
        List<Exam> exams = studyEventService.getExamsForDate(selectedDate);

        for (Exam exam : exams) {
            double progress = exam.getStatus().getProgressValue();
            ProgressBar progressBar = new ProgressBar(progress);
            Label label = new Label(exam.getSubject() + " - " + (int) (progress * 100) + "% Prepared");

            ComboBox<AssignmentStatus> statusComboBox = new ComboBox<>();
            statusComboBox.getItems().setAll(AssignmentStatus.values());
            statusComboBox.setValue(exam.getStatus());

            statusComboBox.setOnAction(event -> {
                AssignmentStatus newStatus = statusComboBox.getValue();
                exam.setStatus(newStatus);
                studyEventService.updateExamStatus(exam);
                loadExamProgress();
            });

            VBox entry = new VBox(label, progressBar, statusComboBox);
            examProgressContainer.getChildren().add(entry);
        }
    }


    protected double calculateExamPreparationProgress(Exam exam) {
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
    protected void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleChangeLanguageToPolish(ActionEvent event) {
        setLanguage(new Locale("pl", "PL"));
    }

    @FXML
    protected void handleChangeLanguageToEnglish(ActionEvent event) {
        setLanguage(new Locale("en", "US"));
    }
}

