package org.example.personalizedstudyplanner.services;

import org.example.personalizedstudyplanner.models.*;
import org.example.personalizedstudyplanner.repositories.StudyEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudyEventServiceTest {

    private StudyEventService studyEventService;
    private StudyEventRepository studyEventRepository;

    @BeforeEach
    void setUp() {
        // Creating a mock implementation of StudyEventRepository
        studyEventRepository = new StudyEventRepository() {
            private List<Assignment> assignments = new ArrayList<>();
            private List<Exam> exams = new ArrayList<>();
            private List<ClassSchedule> classSchedules = new ArrayList<>();

            @Override
            public List<Assignment> getAssignmentsForDate(LocalDate date) {
                return new ArrayList<>(assignments); // Return a copy of assignments
            }

            @Override
            public List<Exam> getExamsForDate(LocalDate date) {
                return new ArrayList<>(exams); // Return a copy of exams
            }

            @Override
            public List<ClassSchedule> getClassesForDate(LocalDate date) {
                return new ArrayList<>(classSchedules); // Return a copy of class schedules
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
                classSchedules.add(classSchedule);
            }
        };

        // Create a StudyEventService with the mocked repository
        studyEventService = new StudyEventService(studyEventRepository);
    }

    @Test
    void testGetAssignmentsForDate() {
        LocalDate date = LocalDate.of(2025, 2, 23);
        Assignment assignment = new Assignment(1, 1, "Math Homework", "Complete exercises 1-10",
                OffsetDateTime.of(2025, 2, 23, 23, 59, 59, 0, ZoneOffset.UTC),
                AssignmentStatus.PENDING);

        studyEventService.addAssignment(assignment);
        List<Assignment> assignments = studyEventService.getAssignmentsForDate(date);

        assertEquals(1, assignments.size());
        assertEquals(assignment, assignments.get(0));
    }


    @Test
    void testGetExamsForDate() {
        LocalDate date = LocalDate.of(2025, 2, 23);
        Exam exam = new Exam(1, 1, "Final Exam",
                OffsetDateTime.of(2025, 2, 23, 10, 0, 0, 0, ZoneOffset.UTC),
                1);

        // Create a valid Address instance with parameters
        Address address = new Address(1, "CountryName", "CityName", "StreetName", 123, "12345");

        studyEventService.addExam(exam, address);
        List<Exam> exams = studyEventService.getExamsForDate(date);

        assertEquals(1, exams.size());
        assertEquals(exam, exams.get(0));
    }


    @Test
    void testAddAssignment() {
        Assignment assignment = new Assignment(2, 1, "History Homework", "Read chapter 5",
                OffsetDateTime.of(2025, 2, 24, 23, 59, 59, 0, ZoneOffset.UTC),
                AssignmentStatus.PENDING);
        studyEventService.addAssignment(assignment);

        List<Assignment> assignments = studyEventService.getAssignmentsForDate(LocalDate.of(2025, 2, 24));
        assertEquals(1, assignments.size());
        assertEquals(assignment, assignments.get(0));
    }

    @Test
    void testAddExam() {
        Exam exam = new Exam(2, 1, "Midterm Exam",
                OffsetDateTime.of(2025, 2, 24, 10, 0, 0, 0, ZoneOffset.UTC),
                1);

        // Create a valid Address instance with parameters
        Address address = new Address(2, "CountryName", "CityName", "StreetName", 456, "67890");

        studyEventService.addExam(exam, address);

        List<Exam> exams = studyEventService.getExamsForDate(LocalDate.of(2025, 2, 24));
        assertEquals(1, exams.size());
        assertEquals(exam, exams.get(0));
    }


    @Test
    void testAddClassSchedule() {
        ClassSchedule classSchedule = new ClassSchedule(
                2, // classScheduleId
                1, // studyPlanId
                "Wednesday", // dayOfWeek
                "Chemistry Class", // className
                OffsetDateTime.of(2025, 2, 24, 10, 0, 0, 0, ZoneOffset.UTC), // startTime
                OffsetDateTime.of(2025, 2, 24, 11, 0, 0, 0, ZoneOffset.UTC), // endTime
                1, // addressId
                "Weekly" // recurrencePattern
        );

        // Create a valid Address instance with parameters
        Address address = new Address(3, "CountryName", "CityName", "StreetName", 789, "11223");

        studyEventService.addClassSchedule(classSchedule, address);

        List<ClassSchedule> classes = studyEventService.getClassesForDate(LocalDate.of(2025, 2, 24));
        assertEquals(1, classes.size());
        assertEquals(classSchedule, classes.get(0));
    }

    @Test
    void testGetClassesForDate() {
        LocalDate date = LocalDate.of(2025, 2, 23);

        // Creating an example ClassSchedule object
        ClassSchedule classSchedule = new ClassSchedule(
                1, // classScheduleId
                1, // studyPlanId
                "Monday", // dayOfWeek
                "Physics Class", // className
                OffsetDateTime.of(2025, 2, 23, 10, 0, 0, 0, ZoneOffset.UTC), // startTime
                OffsetDateTime.of(2025, 2, 23, 11, 0, 0, 0, ZoneOffset.UTC), // endTime
                1, // addressId
                "Weekly" // recurrencePattern
        );

        // Create a valid Address instance with parameters
        Address address = new Address(1, "CountryName", "CityName", "StreetName", 123, "12345");

        studyEventService.addClassSchedule(classSchedule, address);
        List<ClassSchedule> classes = studyEventService.getClassesForDate(date);

        assertEquals(1, classes.size());
        assertEquals(classSchedule, classes.get(0));
    }


}
