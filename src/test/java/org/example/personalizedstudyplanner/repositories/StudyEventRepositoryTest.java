package org.example.personalizedstudyplanner.repositories;

import org.example.personalizedstudyplanner.models.Address;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.models.AssignmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudyEventRepositoryTest {

    @Mock
    private StudyEventRepository repository;

    private Assignment testAssignment;
    private Exam testExam;
    private ClassSchedule testClassSchedule;
    private Address testAddress;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testDate = LocalDate.now();

        // Initialize test objects
        testAssignment = new Assignment(
                1,
                1,
                "Test Assignment",
                "Test Description",
                OffsetDateTime.now(ZoneOffset.UTC),
                AssignmentStatus.PENDING
        );

        testExam = new Exam(
                1,
                1,
                "Test Exam",
                OffsetDateTime.now(ZoneOffset.UTC),
                1
        );

        testClassSchedule = new ClassSchedule(
                1,
                1,
                "Monday",
                "Test Class",
                OffsetDateTime.now(ZoneOffset.UTC),
                OffsetDateTime.now(ZoneOffset.UTC).plusHours(1),
                1,
                "WEEKLY"
        );

        testAddress = new Address(
                1,
                "Test Country",
                "Test City",
                "Test Street",
                123,
                "12345"
        );
    }

    @Test
    void getAssignmentsForDate_ShouldReturnAssignments() {
        // Arrange
        when(repository.getAssignmentsForDate(testDate))
                .thenReturn(Arrays.asList(testAssignment));

        // Act
        List<Assignment> assignments = repository.getAssignmentsForDate(testDate);

        // Assert
        assertNotNull(assignments);
        assertEquals(1, assignments.size());
        assertEquals(testAssignment.getTitle(), assignments.get(0).getTitle());
        verify(repository).getAssignmentsForDate(testDate);
    }

    @Test
    void getExamsForDate_ShouldReturnExams() {
        // Arrange
        when(repository.getExamsForDate(testDate))
                .thenReturn(Arrays.asList(testExam));

        // Act
        List<Exam> exams = repository.getExamsForDate(testDate);

        // Assert
        assertNotNull(exams);
        assertEquals(1, exams.size());
        assertEquals(testExam.getSubject(), exams.get(0).getSubject());
        verify(repository).getExamsForDate(testDate);
    }

    @Test
    void getClassesForDate_ShouldReturnClasses() {
        // Arrange
        when(repository.getClassesForDate(testDate))
                .thenReturn(Arrays.asList(testClassSchedule));

        // Act
        List<ClassSchedule> classes = repository.getClassesForDate(testDate);

        // Assert
        assertNotNull(classes);
        assertEquals(1, classes.size());
        assertEquals(testClassSchedule.getClassName(), classes.get(0).getClassName());
        verify(repository).getClassesForDate(testDate);
    }

    @Test
    void addAssignment_ShouldAddAssignmentSuccessfully() {
        // Act
        repository.addAssignment(testAssignment);

        // Assert
        verify(repository).addAssignment(testAssignment);
    }

    @Test
    void addExam_ShouldAddExamWithAddressSuccessfully() {
        // Act
        repository.addExam(testExam, testAddress);

        // Assert
        verify(repository).addExam(testExam, testAddress);
    }

    @Test
    void addClassSchedule_ShouldAddClassScheduleWithAddressSuccessfully() {
        // Act
        repository.addClassSchedule(testClassSchedule, testAddress);

        // Assert
        verify(repository).addClassSchedule(testClassSchedule, testAddress);
    }
}