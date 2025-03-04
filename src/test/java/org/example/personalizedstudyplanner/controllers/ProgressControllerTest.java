package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.services.StudyEventService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgressControllerTest {
    private ProgressController controller;
    private StudyEventService mockStudyEventService;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        JavaFXTestUtil.initJavaFX();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        mockStudyEventService = mock(StudyEventService.class);
        controller = new ProgressController();

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                setPrivateField(controller, "assignmentProgressContainer", new VBox());
                setPrivateField(controller, "examProgressContainer", new VBox());
                setPrivateField(controller, "studyEventService", mockStudyEventService);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            controller.initialize();
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private Object getPrivateField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    @Test
    void testInitialize_ShouldLoadProgress() {
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> controller.initialize());
            try {
                assertNotNull(getPrivateField(controller, "assignmentProgressContainer"));
                assertNotNull(getPrivateField(controller, "examProgressContainer"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testSetDate_ShouldLoadProgress() {
        LocalDate date = LocalDate.now();
        int studyPlanId = 1;

        Platform.runLater(() -> {
            assertDoesNotThrow(() -> controller.setDate(date, studyPlanId));
            try {
                assertEquals(date, getPrivateField(controller, "selectedDate"));
                assertEquals(studyPlanId, getPrivateField(controller, "studyPlanId"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testLoadAssignmentProgress_ShouldPopulateContainer() {
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> controller.loadAssignmentProgress());
            VBox assignmentProgressContainer = null;
            try {
                assignmentProgressContainer = (VBox) getPrivateField(controller, "assignmentProgressContainer");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertFalse(assignmentProgressContainer.getChildren().isEmpty());
        });
    }

    @Test
    void testLoadExamProgress_ShouldPopulateContainer() {
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> controller.loadExamProgress());
            VBox examProgressContainer = null;
            try {
                examProgressContainer = (VBox) getPrivateField(controller, "examProgressContainer");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertFalse(examProgressContainer.getChildren().isEmpty());
        });
    }

    @Test
    void testCalculateExamPreparationProgress() throws InterruptedException {
        Exam mockExam = mock(Exam.class);
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                when(mockExam.getDate()).thenReturn(OffsetDateTime.now().minusDays(1));
                assertEquals(1.0, controller.calculateExamPreparationProgress(mockExam));

                when(mockExam.getDate()).thenReturn(OffsetDateTime.now());
                assertEquals(1.0, controller.calculateExamPreparationProgress(mockExam));

                when(mockExam.getDate()).thenReturn(OffsetDateTime.now().plusDays(10));
                double expectedProgress = Math.min(1.0, (double) 3 / 10); // 3 days elapsed out of 10
                assertEquals(expectedProgress, controller.calculateExamPreparationProgress(mockExam), 0.01);
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testHandleBack_ShouldCloseStage() {
        ActionEvent mockEvent = mock(ActionEvent.class);
        javafx.scene.control.Button realButton = new javafx.scene.control.Button();
        Stage mockStage = mock(Stage.class);
        javafx.scene.Scene mockScene = mock(javafx.scene.Scene.class);

        Platform.runLater(() -> {
            realButton.setOnAction(event -> controller.handleBack(event));
            realButton.fireEvent(mockEvent);

            when(mockEvent.getSource()).thenReturn(realButton);
            when(realButton.getScene()).thenReturn(mockScene);
            when(mockScene.getWindow()).thenReturn(mockStage);

            assertDoesNotThrow(() -> controller.handleBack(mockEvent));
            verify(mockStage, times(1)).close();
        });
    }


}