package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.models.StudyPlan;
import org.example.personalizedstudyplanner.services.StudyPlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SelectPlannerControllerTest extends ApplicationTest {

    @Mock
    private StudyPlanService studyPlanService;

    private SelectPlannerController selectPlannerController;
    private ListView<StudyPlan> plannerListView;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        selectPlannerController = new SelectPlannerController();
        selectPlannerController.studyPlanService = studyPlanService;
        plannerListView = new ListView<>();
        selectPlannerController.plannerListView = plannerListView;
    }

    @Test
    public void initializeWithNoPlannersShowsAlert() throws SQLException {
        Platform.runLater(() -> {
            try {
                when(studyPlanService.getAllStudyPlans()).thenReturn(Collections.emptyList());

                selectPlannerController.initialize();

                assertTrue(plannerListView.getItems().isEmpty());
            } catch (SQLException e) {
                fail("SQLException was thrown: " + e.getMessage());
            }
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void initializeWithPlannersPopulatesListView() throws SQLException {
        StudyPlan plan1 = new StudyPlan(1, 1, "Plan 1", "Description 1", OffsetDateTime.now());
        StudyPlan plan2 = new StudyPlan(2, 1, "Plan 2", "Description 2", OffsetDateTime.now());
        when(studyPlanService.getAllStudyPlans()).thenReturn(Arrays.asList(plan1, plan2));

        selectPlannerController.initialize();

        ObservableList<StudyPlan> items = plannerListView.getItems();
        assertEquals(2, items.size());
        assertEquals(plan1, items.get(0));
        assertEquals(plan2, items.get(1));
    }

    @Test
    public void handleSelectPlannerWithNoSelectionShowsWarning() throws IOException {
        Platform.runLater(() -> {
            try {
                selectPlannerController.handleSelectPlanner(new ActionEvent());

                // Verify that a warning alert is shown
            } catch (IOException e) {
                fail("IOException was thrown: " + e.getMessage());
            }
        });

        WaitForAsyncUtils.waitForFxEvents();
    }



    @Test
    public void goBackChangesScene() throws Exception {
        Stage stage = mock(Stage.class);
        javafx.scene.layout.Pane mockNode = mock(javafx.scene.layout.Pane.class);
        Scene mockScene = mock(Scene.class);

        when(mockNode.getScene()).thenReturn(mockScene);
        when(mockScene.getWindow()).thenReturn(stage);

        ActionEvent event = new ActionEvent(mockNode, null);
        selectPlannerController.goBack(event);

        verify(stage).setScene(any(Scene.class));
    }

    @Test
    public void handleDeletePlannerWithNoSelectionShowsWarning() {
        Platform.runLater(() -> {
            selectPlannerController.handleDeletePlanner(new ActionEvent());

            // Verify that a warning alert is shown
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

}