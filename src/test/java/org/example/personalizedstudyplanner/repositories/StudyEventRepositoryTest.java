package org.example.personalizedstudyplanner.repositories;

import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.repositories.StudyEventRepository;
import org.example.personalizedstudyplanner.repositories_implementations.StudyEventRepositoryImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class StudyEventRepositoryTest {
    private StudyEventRepository studyEventRepository;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/personalized_study_planner", "postgres", "dmbonloz123");
        studyEventRepository = new StudyEventRepositoryImplementation(connection);
    }

    @Test
    void getAssignmentsForDate() {
        LocalDate date = LocalDate.now();
        int studyPlanId = 101;
        List<Assignment> assignments = studyEventRepository.getAssignmentsForDate(date, studyPlanId);
        assertNotNull(assignments);
    }

    @Test
    void getExamsForDate() {
        LocalDate date = LocalDate.now();
        int studyPlanId = 202;
        List<Exam> exams = studyEventRepository.getExamsForDate(date, studyPlanId);
        assertNotNull(exams);
    }

    @Test
    void getClassesForDate() {
        LocalDate date = LocalDate.now();
        int studyPlanId = 303;
        List<ClassSchedule> classes = studyEventRepository.getClassesForDate(date, studyPlanId);
        assertNotNull(classes);
    }
}
