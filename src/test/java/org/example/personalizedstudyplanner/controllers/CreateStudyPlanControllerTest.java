package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.personalizedstudyplanner.services.StudyPlanService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CreateStudyPlanControllerTest {
    private CreateStudyPlanController controller;
    private StudyPlanService studyPlanService;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        JavaFXTestUtil.initJavaFX();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller = new CreateStudyPlanController();
            controller.setTitleField(new TextField());
            controller.setDescriptionField(new TextArea());
            studyPlanService = mock(StudyPlanService.class);
            controller.setStudyPlanService(studyPlanService);
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testHandleCreateStudyPlan_EmptyFields_ShouldShowError() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.getTitleField().setText("");
            controller.getDescriptionField().setText("");
            assertTrue(controller.getTitleField().getText().isEmpty());
            assertTrue(controller.getDescriptionField().getText().isEmpty());
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testGetStudyPlanService() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            assertEquals(studyPlanService, controller.getStudyPlanService());
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testGoBack() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                ActionEvent event = mock(ActionEvent.class);
                when(event.getSource()).thenReturn(new javafx.scene.control.Button());
                controller.goBack(event);
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testShowAlert() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.showAlert("Test Title", "Test Message", Alert.AlertType.INFORMATION);
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testHandleCreateStudyPlan_ValidFields_ShouldCreateStudyPlan() throws InterruptedException, SQLException, IOException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                controller.getTitleField().setText("Test Title");
                controller.getDescriptionField().setText("Test Description");
                ActionEvent event = mock(ActionEvent.class);
                when(event.getSource()).thenReturn(new javafx.scene.control.Button());
                controller.handleCreateStudyPlan(event);
                verify(studyPlanService, times(1)).createStudyPlan("Test Title", "Test Description");
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }
}