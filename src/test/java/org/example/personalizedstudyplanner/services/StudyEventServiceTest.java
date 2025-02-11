package org.example.personalizedstudyplanner.services;

import org.example.personalizedstudyplanner.models.Address;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.models.AssignmentStatus;
import org.example.personalizedstudyplanner.repositories.StudyEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudyEventServiceTest {

    private StudyEventService studyEventService;
    private TestStudyEventRepository testRepository;

    @BeforeEach
    void setUp() {
        testRepository = new TestStudyEventRepository();
        try {
            java.lang.reflect.Field field = StudyEventService.class.getDeclaredField("studyEventRepository");
            field.setAccessible(true);
            studyEventService = new StudyEventService() {
                {
                    try {
                        field.set(this, testRepository);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            };
        } catch (Exception e) {
            fail("Failed to set up test: " + e.getMessage());
        }
    }

    @Test
    void getAssignmentsForDate_ShouldReturnListOfAssignments() {
        // Arrange
        LocalDate testDate = LocalDate.now();
        Assignment testAssignment = new Assignment(1, 1, "Test Assignment", "Description",
                OffsetDateTime.now(ZoneOffset.UTC), AssignmentStatus.PENDING);
        testRepository.addTestAssignment(testAssignment);

        // Act
        List<Assignment> assignments = studyEventService.getAssignmentsForDate(testDate);

        // Assert
        assertEquals(1, assignments.size());
        assertEquals(testAssignment.getTitle(), assignments.get(0).getTitle());
    }

    @Test
    void getExamsForDate_ShouldReturnListOfExams() {
        // Arrange
        LocalDate testDate = LocalDate.now();
        Exam testExam = new Exam(1, 1, "Test Exam", OffsetDateTime.now(ZoneOffset.UTC), 1);
        testRepository.addTestExam(testExam);

        // Act
        List<Exam> exams = studyEventService.getExamsForDate(testDate);

        // Assert
        assertEquals(1, exams.size());
        assertEquals(testExam.getSubject(), exams.get(0).getSubject());
    }

    @Test
    void getClassesForDate_ShouldReturnListOfClasses() {
        // Arrange
        LocalDate testDate = LocalDate.now();
        ClassSchedule testClass = new ClassSchedule(1, 1, "Monday", "Test Class",
                OffsetDateTime.now(ZoneOffset.UTC), OffsetDateTime.now(ZoneOffset.UTC).plusHours(1),
                1, "WEEKLY");
        testRepository.addTestClass(testClass);

        // Act
        List<ClassSchedule> classes = studyEventService.getClassesForDate(testDate);

        // Assert
        assertEquals(1, classes.size());
        assertEquals(testClass.getClassName(), classes.get(0).getClassName());
    }

    @Test
    void addAssignment_ShouldAddToRepository() {
        // Arrange
        Assignment assignment = new Assignment(1, 1, "Test Assignment", "Description",
                OffsetDateTime.now(ZoneOffset.UTC), AssignmentStatus.PENDING);

        // Act
        studyEventService.addAssignment(assignment);

        // Assert
        assertTrue(testRepository.containsAssignment(assignment));
    }

    @Test
    void addExam_ShouldAddToRepository() {
        // Arrange
        Exam exam = new Exam(1, 1, "Test Exam", OffsetDateTime.now(ZoneOffset.UTC), 1);
        Address address = new Address(1, "Country", "City", "Street", 123, "12345");

        // Act
        studyEventService.addExam(exam, address);

        // Assert
        assertTrue(testRepository.containsExam(exam));
    }

    @Test
    void addClassSchedule_ShouldAddToRepository() {
        // Arrange
        ClassSchedule classSchedule = new ClassSchedule(1, 1, "Monday", "Test Class",
                OffsetDateTime.now(ZoneOffset.UTC), OffsetDateTime.now(ZoneOffset.UTC).plusHours(1),
                1, "WEEKLY");
        Address address = new Address(1, "Country", "City", "Street", 123, "12345");

        // Act
        studyEventService.addClassSchedule(classSchedule, address);

        // Assert
        assertTrue(testRepository.containsClass(classSchedule));
    }

    // Test repository implementation for testing without database
    private static class TestStudyEventRepository implements StudyEventRepository {
        private final List<Assignment> assignments = new ArrayList<>();
        private final List<Exam> exams = new ArrayList<>();
        private final List<ClassSchedule> classes = new ArrayList<>();

        @Override
        public List<Assignment> getAssignmentsForDate(LocalDate date) {
            return new ArrayList<>(assignments);
        }

        @Override
        public List<Exam> getExamsForDate(LocalDate date) {
            return new ArrayList<>(exams);
        }

        @Override
        public List<ClassSchedule> getClassesForDate(LocalDate date) {
            return new ArrayList<>(classes);
        }

        @Override
        public void addAssignment(Assignment assignment) {
            assignments.add(assignment);
        }

        @Override
        public void addExam(Exam exam, Address address) {
            exams.add(exam);
        }

        @Override
        public void addClassSchedule(ClassSchedule classSchedule, Address address) {
            classes.add(classSchedule);
        }

        // Helper methods for testing
        public void addTestAssignment(Assignment assignment) {
            assignments.add(assignment);
        }

        public void addTestExam(Exam exam) {
            exams.add(exam);
        }

        public void addTestClass(ClassSchedule classSchedule) {
            classes.add(classSchedule);
        }

        public boolean containsAssignment(Assignment assignment) {
            return assignments.contains(assignment);
        }

        public boolean containsExam(Exam exam) {
            return exams.contains(exam);
        }

        public boolean containsClass(ClassSchedule classSchedule) {
            return classes.contains(classSchedule);
        }
    }
}