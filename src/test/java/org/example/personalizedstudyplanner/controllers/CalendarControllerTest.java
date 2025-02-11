package org.example.personalizedstudyplanner.controllers;

import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.AssignmentStatus;
import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.services.StudyEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalendarControllerTest extends ApplicationTest {

    @Mock
    private StudyEventService studyEventService;

    @InjectMocks
    private CalendarController calendarController;

    @BeforeEach
    public void setUp() {
        calendarController = new CalendarController();
        calendarController.studyEventService = studyEventService;
    }



    @Test
    public void getEventsForDayReturnsEvents() throws SQLException {
        LocalDate date = LocalDate.now();
        when(studyEventService.getAssignmentsForDate(date)).thenReturn(List.of(new Assignment(1, 1, "Assignment 1", "Description", date.atStartOfDay().atOffset(OffsetDateTime.now().getOffset()), AssignmentStatus.PENDING)));
        when(studyEventService.getExamsForDate(date)).thenReturn(List.of(new Exam(1, 1, "Exam 1", date.atStartOfDay().atOffset(OffsetDateTime.now().getOffset()), 0)));
        when(studyEventService.getClassesForDate(date)).thenReturn(List.of(new ClassSchedule(1, 1, "Monday", "Class 1", date.atStartOfDay().atOffset(OffsetDateTime.now().getOffset()), date.atStartOfDay().atOffset(OffsetDateTime.now().getOffset()).plusHours(1), 0, "Weekly")));

        String events = calendarController.getEventsForDay(date);
        assertTrue(events.contains("Assignment 1"));
        assertTrue(events.contains("Exam 1"));
        assertTrue(events.contains("Class 1"));
    }

}