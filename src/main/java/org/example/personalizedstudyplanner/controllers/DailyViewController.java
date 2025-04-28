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
import org.example.personalizedstudyplanner.exceptions.DatabaseException;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.services.StudyEventService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DailyViewController {
    public static final String DAILY_VIEW_TITLE = "dailyView.title";
    @FXML
    public Button languagePolishButton;
    @FXML
    public Button languageEnglishButton;
    @FXML
    public Button languageChineseButton;
    @FXML
    private Label dateLabel;
    @FXML
    private ListView<String> eventsListView;
    @FXML
    private Button addEventButton;
    @FXML
    private Button viewProgressButton;
    @FXML
    private Button goBackButton;
    private final StudyEventService studyEventService = new StudyEventService();
    private LocalDate selectedDate;
    private int studyPlanId;
    private ResourceBundle rb;

    public void setDate(LocalDate date, int studyPlanId, Locale locale) {
        this.selectedDate = date;
        this.studyPlanId = studyPlanId;
        setLanguage(locale);
        dateLabel.setText(rb.getString(DAILY_VIEW_TITLE) + " " + selectedDate);
        loadEvents();
    }

    private void setLanguage(Locale locale) {
        rb = ResourceBundle.getBundle("messages", locale);
        updateUI();
    }

    private void updateUI() {
        dateLabel.setText(rb.getString(DAILY_VIEW_TITLE) + " " + selectedDate);
        addEventButton.setText(rb.getString("dailyView.addEvent"));
        viewProgressButton.setText(rb.getString("viewProgress"));
        goBackButton.setText(rb.getString("goBack"));
    }

    private void loadEvents() {
        eventsListView.getItems().clear();
        List<Assignment> assignments = studyEventService.getAssignmentsForDate(selectedDate);
        List<Exam> exams = studyEventService.getExamsForDate(selectedDate);
        List<ClassSchedule> classes = studyEventService.getClassesForDate(selectedDate);

        for (Assignment a : assignments)
            eventsListView.getItems().add("📌 " + rb.getString("assignment") + ": " + a.getTitle());
        for (Exam e : exams) eventsListView.getItems().add("📝 " + rb.getString("exam") + ": " + e.getSubject());
        for (ClassSchedule c : classes)
            eventsListView.getItems().add("📚 " + rb.getString("class") + ": " + c.getClassName());

        if (eventsListView.getItems().isEmpty()) {
            eventsListView.getItems().add(rb.getString("dailyView.noEvents"));
        }
    }

    @FXML
    protected void handleAddEvent() {
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
    protected void handleBack(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleViewProgress(ActionEvent event) {
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
            throw new DatabaseException("Invalid");
        }
    }

    @FXML
    protected void handleChangeLanguageToPolish(ActionEvent event) {
        setLanguage(new Locale("pl", "PL"));
        dateLabel.setText(rb.getString(DAILY_VIEW_TITLE) + " " + selectedDate);
        loadEvents();
    }

    @FXML
    protected void handleChangeLanguageToEnglish(ActionEvent event) {
        setLanguage(new Locale("en", "US"));
        dateLabel.setText(rb.getString(DAILY_VIEW_TITLE) + " " + selectedDate);
        loadEvents();
    }

    @FXML
    public void handleChangeLanguageToChinese(ActionEvent actionEvent) {
        setLanguage(new Locale("zh", "CN"));
        dateLabel.setText(rb.getString(DAILY_VIEW_TITLE) + " " + selectedDate);
        loadEvents();
    }
}
