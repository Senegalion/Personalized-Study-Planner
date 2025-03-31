package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.context.StudyPlanContext;
import org.example.personalizedstudyplanner.models.*;
import org.example.personalizedstudyplanner.services.StudyEventService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

public class CalendarController {
    @FXML
    protected GridPane calendarGrid;
    @FXML
    private HBox topBar;

    @FXML
    private Label titleLabel;

    @FXML
    private Button todayButton;

    @FXML
    private Button backButton;

    @FXML
    private Button languagePLButton;

    @FXML
    private Button languageENButton;
    @FXML
    private VBox mainContainer;
    private ResourceBundle rb;
    private Locale currentLocale = Locale.getDefault();
    private final StudyEventService studyEventService = new StudyEventService();

    @FXML
    public void initialize() {
        setLanguage(currentLocale);
        Platform.runLater(() -> {
            try {
                generateCalendar();
                checkReminders();
            } catch (SQLException e) {
                showErrorAlert("Failed to load calendar.");
            }
        });
    }

    private void setLanguage(Locale locale) {
        rb = ResourceBundle.getBundle("messages", locale);
        updateUI();
    }

    private void updateUI() {
        titleLabel.setText(rb.getString("calendar.title"));
        todayButton.setText(rb.getString("calendar.today"));
        backButton.setText(rb.getString("calendar.goBack"));
    }

    @FXML
    public void handleChangeLanguagePL(ActionEvent event) {
        setLanguage(new Locale("pl", "PL"));
    }

    @FXML
    public void handleChangeLanguageEN(ActionEvent event) {
        setLanguage(new Locale("en", "US"));
    }

    protected void generateCalendar() throws SQLException {
        if (calendarGrid == null) {
            System.err.println("Error: calendarGrid is null!");
            return;
        }
        calendarGrid.getChildren().clear();
        calendarGrid.getColumnConstraints().clear();
        calendarGrid.getRowConstraints().clear();

        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / 7);
            calendarGrid.getColumnConstraints().add(col);
        }

        for (int i = 0; i < 7; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(80);
            calendarGrid.getRowConstraints().add(row);
        }

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5px; -fx-alignment: center;");
            calendarGrid.add(dayLabel, i, 0);
        }

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        int column = firstDayOfMonth.getDayOfWeek().getValue() % 7;
        int row = 1;

        String noEventsText = rb.getString("calendar.noEvents");

        for (int day = 1; day <= today.lengthOfMonth(); day++) {
            LocalDate date = today.withDayOfMonth(day);

            VBox dayBox = new VBox();
            dayBox.setPrefSize(100, 80);
            dayBox.setStyle("-fx-border-color: black; -fx-padding: 5px; -fx-background-color: #f0f0f0;");

            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            String eventText = getEventsForDay(date);
            Label eventLabel = new Label(eventText.isEmpty() ? noEventsText : eventText);
            eventLabel.setWrapText(true);

            dayBox.getChildren().addAll(dayLabel, eventLabel);
            dayBox.setOnMouseClicked(e -> openDailyView(date));

            calendarGrid.add(dayBox, column, row);

            column++;
            if (column == 7) {
                column = 0;
                row++;
            }
        }
    }

    private void openDailyView(LocalDate date) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/personalizedstudyplanner/DailyView.fxml"));
            Parent root = loader.load();

            DailyViewController controller = loader.getController();
            controller.setDate(date, StudyPlanContext.getCurrentStudyPlanId());

            Stage stage = new Stage();
            stage.setTitle("Daily View - " + date);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            showErrorAlert("Failed to open daily view.");
            e.printStackTrace();
        }
    }

    private String getEventsForDay(LocalDate date) throws SQLException {
        List<Assignment> assignments = studyEventService.getAssignmentsForDate(date);
        List<Exam> exams = studyEventService.getExamsForDate(date);
        List<ClassSchedule> classes = studyEventService.getClassesForDate(date);

        StringBuilder sb = new StringBuilder();
        for (Assignment a : assignments) sb.append("📌 Assignment: ").append(a.getTitle()).append("\n");
        for (Exam e : exams) sb.append("📝 Exam: ").append(e.getSubject()).append("\n");
        for (ClassSchedule c : classes) sb.append("📚 Class: ").append(c.getClassName()).append("\n");

        return sb.toString();
    }

    void openEventForm(LocalDate date, String eventType) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Add " + eventType);
        dialog.setHeaderText("Enter details for the " + eventType);

        TextField titleField = new TextField();
        titleField.setPromptText(eventType.equals("Assignment") ? "Title" : eventType.equals("Exam") ? "Subject" : "Class Name");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        ChoiceBox<String> recurrenceChoiceBox = new ChoiceBox<>();
        recurrenceChoiceBox.getItems().addAll("None", "Daily", "Weekly", "Monthly");
        recurrenceChoiceBox.setValue("None");

        // Address Fields
        TextField countryField = new TextField();
        countryField.setPromptText("Country");

        TextField cityField = new TextField();
        cityField.setPromptText("City");

        TextField streetField = new TextField();
        streetField.setPromptText("Street");

        TextField numberField = new TextField();
        numberField.setPromptText("Number");

        TextField postalCodeField = new TextField();
        postalCodeField.setPromptText("Postal Code");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Title/Subject:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);

        if (!eventType.equals("Assignment")) {
            grid.add(new Label("Country:"), 0, 2);
            grid.add(countryField, 1, 2);
            grid.add(new Label("City:"), 0, 3);
            grid.add(cityField, 1, 3);
            grid.add(new Label("Street:"), 0, 4);
            grid.add(streetField, 1, 4);
            grid.add(new Label("Number:"), 0, 5);
            grid.add(numberField, 1, 5);
            grid.add(new Label("Postal Code:"), 0, 6);
            grid.add(postalCodeField, 1, 6);
        }

        if (eventType.equals("Class Schedule")) {
            grid.add(new Label("Recurrence:"), 0, 7);
            grid.add(recurrenceChoiceBox, 1, 7);
        }

        dialog.getDialogPane().setContent(grid);
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                List<String> result = new ArrayList<>();
                result.add(titleField.getText());
                result.add(descriptionField.getText());

                if (!eventType.equals("Assignment")) {
                    result.add(countryField.getText());
                    result.add(cityField.getText());
                    result.add(streetField.getText());
                    result.add(numberField.getText());
                    result.add(postalCodeField.getText());
                }

                if (eventType.equals("Class Schedule")) {
                    result.add(recurrenceChoiceBox.getValue());
                }
                return result;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            String title = result.get(0);
            String description = result.get(1);

            Address address = null;
            if (!eventType.equals("Assignment")) {
                address = new Address(0, result.get(2), result.get(3), result.get(4), Integer.parseInt(result.get(5)), result.get(6));
            }

            try {
                OffsetDateTime eventDate = date.atStartOfDay().atOffset(OffsetDateTime.now().getOffset());

                switch (eventType) {
                    case "Assignment":
                        studyEventService.addAssignment(new Assignment(0, StudyPlanContext.getCurrentStudyPlanId(), title, description, eventDate, AssignmentStatus.PENDING));
                        break;
                    case "Exam":
                        studyEventService.addExam(new Exam(0, StudyPlanContext.getCurrentStudyPlanId(), title, eventDate, 0, AssignmentStatus.PENDING), address);
                        break;
                    case "Class Schedule":
                        studyEventService.addClassSchedule(new ClassSchedule(0, StudyPlanContext.getCurrentStudyPlanId(), "Monday", title, eventDate, eventDate.plusHours(1), 0, result.get(7)), address);
                        break;
                }
                generateCalendar();
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("Failed to save event.");
            }
        });
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/personalizedstudyplanner/SelectPlanner.fxml")));
        stage.setScene(new Scene(root, 800, 600));
    }

    @FXML
    protected void handleToday(ActionEvent event) {
        try {
            generateCalendar();
        } catch (SQLException e) {
            showErrorAlert("Failed to refresh calendar.");
            e.printStackTrace();
        }
    }

    private void checkReminders() {
        try {
            int daysAhead = 3;
            int studyPlanId = StudyPlanContext.getCurrentStudyPlanId();

            List<Assignment> assignments = studyEventService.getUpcomingAssignments(daysAhead, studyPlanId);
            List<Exam> exams = studyEventService.getUpcomingExams(daysAhead, studyPlanId);
            List<ClassSchedule> classes = studyEventService.getUpcomingClasses(daysAhead, studyPlanId);

            if (!assignments.isEmpty() || !exams.isEmpty() || !classes.isEmpty()) {
                StringBuilder reminderText = new StringBuilder("📢 Upcoming Events:\n");

                for (Assignment a : assignments) {
                    reminderText.append("📌 Assignment: ").append(a.getTitle())
                            .append(" - Due ").append(a.getDueDate().toLocalDate()).append("\n");
                }

                for (Exam e : exams) {
                    reminderText.append("📝 Exam: ").append(e.getSubject())
                            .append(" - On ").append(e.getDate().toLocalDate()).append("\n");
                }

                for (ClassSchedule c : classes) {
                    reminderText.append("📚 Class: ").append(c.getClassName())
                            .append(" - Scheduled on ").append(c.getStartTime().toLocalDate()).append("\n");
                }

                showReminderAlert(reminderText.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void showReminderAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reminders");
        alert.setHeaderText("Upcoming Events");
        alert.setContentText(message);
        alert.showAndWait();
    }
}