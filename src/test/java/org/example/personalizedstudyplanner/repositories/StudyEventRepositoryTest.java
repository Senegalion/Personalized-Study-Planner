package org.example.personalizedstudyplanner.repositories;

import org.example.personalizedstudyplanner.models.*;
import org.example.personalizedstudyplanner.repositories_implementations.StudyEventRepositoryImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudyEventRepositoryTest {
    private StudyEventRepository studyEventRepository;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/personalized_study_planner", "postgres", "postgres");
        studyEventRepository = new StudyEventRepositoryImplementation(connection);

    }

    @Test
    void getAssignmentsForDate() {
        LocalDate date = LocalDate.now();
        List<Assignment> assignments = studyEventRepository.getAssignmentsForDate(date);
        assertNotNull(assignments);
    }

    @Test
    void getExamsForDate() {
        LocalDate date = LocalDate.now();
        List<Exam> exams = studyEventRepository.getExamsForDate(date);
        assertNotNull(exams);
    }

    @Test
    void getClassesForDate() {
        LocalDate date = LocalDate.now();
        List<ClassSchedule> classes = studyEventRepository.getClassesForDate(date);
        assertNotNull(classes);
    }

}