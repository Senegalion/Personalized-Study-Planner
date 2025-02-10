package org.example.personalizedstudyplanner.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.models.*;
import org.example.personalizedstudyplanner.services.StudyEventService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalendarController {
    @FXML
    private GridPane calendarGrid;

    @FXML
    private VBox mainContainer;

    private final StudyEventService studyEventService = new StudyEventService();
    private int studyPlanId;

    @FXML
    public void initialize() {
        try {
            generateCalendar();
        } catch (SQLException e) {
            showErrorAlert("Failed to load calendar.");
        }
    }

    private void generateCalendar() throws SQLException {
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

        for (int day = 1; day <= today.lengthOfMonth(); day++) {
            LocalDate date = today.withDayOfMonth(day);

            VBox dayBox = new VBox();
            dayBox.setPrefSize(100, 80);
            dayBox.setStyle("-fx-border-color: black; -fx-padding: 5px; -fx-background-color: #f0f0f0;");

            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            String eventText = getEventsForDay(date);
            Label eventLabel = new Label(eventText.isEmpty() ? "No Events" : eventText);
            eventLabel.setWrapText(true);

            dayBox.getChildren().addAll(dayLabel, eventLabel);
            dayBox.setOnMouseClicked(e -> showAddEventDialog(date));

            calendarGrid.add(dayBox, column, row);

            column++;
            if (column == 7) {
                column = 0;
                row++;
            }
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

    private void showAddEventDialog(LocalDate date) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Assignment", "Assignment", "Exam", "Class Schedule");
        dialog.setTitle("Add Event");
        dialog.setHeaderText("Select Event Type:");
        dialog.showAndWait().ifPresent(choice -> openEventForm(date, choice));
    }

    private void openEventForm(LocalDate date, String eventType) {
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
                address = new Address(
                        0,
                        result.get(2),
                        result.get(3),
                        result.get(4),
                        Integer.parseInt(result.get(5)),
                        result.get(6)
                );
            }

            try {
                OffsetDateTime eventDate = date.atStartOfDay().atOffset(OffsetDateTime.now().getOffset());

                switch (eventType) {
                    case "Assignment":
                        studyEventService.addAssignment(new Assignment(0, studyPlanId, title, description, eventDate, AssignmentStatus.PENDING));
                        break;
                    case "Exam":
                        studyEventService.addExam(new Exam(0, studyPlanId, title, eventDate, 0), address);
                        break;
                    case "Class Schedule":
                        studyEventService.addClassSchedule(new ClassSchedule(0, studyPlanId, "Monday", title, eventDate, eventDate.plusHours(1), 0, result.get(7)), address);
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
    private void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/personalizedstudyplanner/SelectPlanner.fxml")));
        stage.setScene(new Scene(root, 800, 600));
    }

    public void setStudyPlanId(int studyPlanId) {
        this.studyPlanId = studyPlanId;
    }
}