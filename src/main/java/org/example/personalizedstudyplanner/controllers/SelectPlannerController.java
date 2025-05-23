package org.example.personalizedstudyplanner.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.context.StudyPlanContext;
import org.example.personalizedstudyplanner.exceptions.DatabaseException;
import org.example.personalizedstudyplanner.models.StudyPlan;
import org.example.personalizedstudyplanner.services.StudyPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class SelectPlannerController {
    Logger logger = LoggerFactory.getLogger(SelectPlannerController.class);
    @FXML
    private ListView<StudyPlan> plannerListView;
    @FXML
    private Button languagePLButton;
    @FXML
    private Button languageENButton;
    @FXML
    private Button languageZHButton;
    @FXML
    private Button selectButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button backButton;
    @FXML
    private Label titleLabel;
    private final StudyPlanService studyPlanService = new StudyPlanService();
    private ObservableList<StudyPlan> observablePlanners;

    private ResourceBundle rb;
    private Locale currentLocale = Locale.getDefault();

    @FXML
    public void initialize() throws SQLException {
        loadLanguage();
        updatePlannerList();
    }

    private void loadLanguage() {
        rb = ResourceBundle.getBundle("messages", currentLocale);
        updateUI();
    }

    private void updateUI() {
        titleLabel.setText(rb.getString("selectPlannerTitle"));
        languagePLButton.setText(rb.getString("language.PL"));
        languageENButton.setText(rb.getString("language.EN"));
        languageZHButton.setText(rb.getString("language.ZH"));
        selectButton.setText(rb.getString("button.select"));
        deleteButton.setText(rb.getString("button.delete"));
        backButton.setText(rb.getString("button.back"));
    }

    private void updatePlannerList() throws SQLException {
        List<StudyPlan> planners = studyPlanService.getAllStudyPlans();

        if (planners.isEmpty()) {
            showAlert(rb.getString("alert.noPlannersFound.title"), rb.getString("alert.noPlannersFound.content"));
        }

        observablePlanners = FXCollections.observableArrayList(planners);
        plannerListView.setItems(observablePlanners);
        plannerListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(StudyPlan item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String lang = currentLocale.getLanguage();
                    String title = item.getTranslations().containsKey(lang)
                            ? item.getTranslations().get(lang).getTitle()
                            : "[No title]";
                    String desc = item.getTranslations().containsKey(lang)
                            ? item.getTranslations().get(lang).getDescription()
                            : "[No description]";
                    setText(title + " - " + desc);
                }
            }
        });
    }

    @FXML
    public void handleSelectPlanner(ActionEvent event) throws IOException {
        StudyPlan selectedPlanner = plannerListView.getSelectionModel().getSelectedItem();

        if (selectedPlanner != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("alert.plannerSelected.title"));
            alert.setHeaderText(null);
            alert.setContentText(rb.getString("alert.plannerSelected.content") + selectedPlanner);
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/personalizedstudyplanner/CalendarView.fxml"));
            Parent root = loader.load();

            StudyPlanContext.setCurrentStudyPlanId(selectedPlanner.getStudyPlanId());

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(rb.getString("alert.selectionError.title"));
            alert.setHeaderText(null);
            alert.setContentText(rb.getString("alert.selectionError.content"));
            alert.showAndWait();
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/personalizedstudyplanner/Dashboard.fxml")));
        stage.setScene(new Scene(root, 800, 600));
    }

    @FXML
    public void handleDeletePlanner(ActionEvent event) {
        StudyPlan selectedPlanner = plannerListView.getSelectionModel().getSelectedItem();

        if (selectedPlanner != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle(rb.getString("alert.confirmDeletion.title"));
            confirmation.setHeaderText(null);
            String lang = currentLocale.getLanguage();
            String title = selectedPlanner.getTranslations().containsKey(lang)
                    ? selectedPlanner.getTranslations().get(lang).getTitle()
                    : "[No title]";
            confirmation.setContentText(rb.getString("alert.confirmDeletion.content") + title + "'?");

            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        studyPlanService.deleteStudyPlan(selectedPlanner.getStudyPlanId());
                        observablePlanners.remove(selectedPlanner);
                    } catch (SQLException e) {
                        logger.error("SQL Exception");
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle(rb.getString("alert.deletionError.title"));
                        error.setHeaderText(null);
                        error.setContentText(rb.getString("alert.deletionError.content"));
                        error.showAndWait();
                    }
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(rb.getString("alert.selectionError.title"));
            alert.setHeaderText(null);
            alert.setContentText(rb.getString("alert.selectionError.content"));
            alert.showAndWait();
        }
    }

    @FXML
    public void handleChangeLanguagePL() {
        changeLanguage(new Locale("pl", "PL"));
    }

    @FXML
    public void handleChangeLanguageEN() {
        changeLanguage(new Locale("en", "US"));
    }

    @FXML
    private void handleChangeLanguageZH(ActionEvent event) {
        changeLanguage(new Locale("zh", "CN"));
    }

    private void changeLanguage(Locale locale) {
        currentLocale = locale;
        loadLanguage();
        try {
            updatePlannerList();
        } catch (SQLException e) {
            throw new DatabaseException("Invalid");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
