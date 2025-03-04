package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.AssignmentStatus;
import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.services.StudyEventService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DailyViewControllerTest {
    private DailyViewController controller;
    private StudyEventService mockStudyEventService;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        JavaFXTestUtil.initJavaFX();
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        mockStudyEventService = mock(StudyEventService.class);
        controller = new DailyViewController();
        try {
            setPrivateField(controller, "dateLabel", new Label());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            setPrivateField(controller, "eventsListView", new ListView<>());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            setPrivateField(controller, "studyEventService", mockStudyEventService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
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
    void testSetDate_ShouldLoadEvents() throws Exception {
        LocalDate date = LocalDate.now();
        int studyPlanId = 1;

        controller.setDate(date, studyPlanId);

        Label dateLabel = (Label) getPrivateField(controller, "dateLabel");
        assertEquals("Events for " + date, dateLabel.getText());
        verify(mockStudyEventService, times(1)).getAssignmentsForDate(date);
        verify(mockStudyEventService, times(1)).getExamsForDate(date);
        verify(mockStudyEventService, times(1)).getClassesForDate(date);
    }

    @Test
    void testLoadEvents_ShouldPopulateListView() throws Exception {
        LocalDate date = LocalDate.now();
        Assignment assignment = new Assignment(1, 1, "Assignment 1", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        Exam exam = new Exam(1, 1, "Exam 1", OffsetDateTime.now(), 1, AssignmentStatus.PENDING);
        ClassSchedule classSchedule = new ClassSchedule(1, 1, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");

        when(mockStudyEventService.getAssignmentsForDate(date)).thenReturn(Collections.singletonList(assignment));
        when(mockStudyEventService.getExamsForDate(date)).thenReturn(Collections.singletonList(exam));
        when(mockStudyEventService.getClassesForDate(date)).thenReturn(Collections.singletonList(classSchedule));

        controller.setDate(date, 1);

        ListView<String> eventsListView = (ListView<String>) getPrivateField(controller, "eventsListView");
        assertEquals(3, eventsListView.getItems().size());
        assertTrue(eventsListView.getItems().contains("📌 Assignment: " + assignment.getTitle()));
        assertTrue(eventsListView.getItems().contains("📝 Exam: " + exam.getSubject()));
        assertTrue(eventsListView.getItems().contains("📚 Class: " + classSchedule.getClassName()));
    }
}