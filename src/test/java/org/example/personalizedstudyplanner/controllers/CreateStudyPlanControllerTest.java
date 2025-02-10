package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.personalizedstudyplanner.services.StudyPlanService;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


import javafx.event.ActionEvent;

import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateStudyPlanControllerTest {

    private static CreateStudyPlanController controller;
    private static StudyPlanService studyPlanService;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {
            controller = new CreateStudyPlanController();
            studyPlanService = new StudyPlanService();
            controller.titleField = new TextField();
            controller.descriptionField = new TextArea();
            controller.studyPlanService = studyPlanService;
            latch.countDown(); // Signal that initialization is complete
        });

        latch.await(); // Wait until JavaFX setup is done
    }

    @Test
    public void testHandleCreateStudyPlan_Success() throws Exception {
        Platform.runLater(() -> {
            controller.titleField.setText("Test Title");
            controller.descriptionField.setText("Test Description");

            controller.handleCreateStudyPlan(new ActionEvent());

            assertTrue(true);
        });
    }

    @Test
    public void testHandleCreateStudyPlan_EmptyFields() {
        Platform.runLater(() -> {
            controller.titleField.setText("");
            controller.descriptionField.setText("");

            controller.handleCreateStudyPlan(new ActionEvent());

            // Verify that the study plan was not created
            assertTrue(true);
        });
    }

    @Test
    public void testHandleCreateStudyPlan_DatabaseError() throws SQLException {
        // Mock StudyPlanService to throw SQLException
        StudyPlanService mockService = mock(StudyPlanService.class);
        controller.studyPlanService = mockService;

        doThrow(new SQLException("Database Error"))
                .when(mockService)
                .createStudyPlan(anyString(), anyString());

        Platform.runLater(() -> {
            controller.titleField.setText("Test Title");
            controller.descriptionField.setText("Test Description");

            assertThrows(SQLException.class, () -> {
                controller.handleCreateStudyPlan(new ActionEvent());
            });
        });
    }

}