package org.example.personalizedstudyplanner.repositories_implementations;

import org.example.personalizedstudyplanner.database.DatabaseUtil;
import org.example.personalizedstudyplanner.models.*;
import org.example.personalizedstudyplanner.repositories.StudyEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudyEventRepositoryImplementationTest {

    private Connection connection;
    private StudyEventRepository studyEventRepository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DatabaseUtil.connect();
        if (connection == null) {
            fail("Failed to connect to the database");
        }
        clearDatabase();
        studyEventRepository = new StudyEventRepositoryImplementation(connection);
    }

    private void clearDatabase() throws SQLException {
        connection.createStatement().execute("TRUNCATE TABLE assignment, exam, class_schedule, address RESTART IDENTITY CASCADE");
    }

    @Test
    void addAssignment_addsNewAssignment() throws SQLException {
        Assignment assignment = new Assignment(1, 1, "Title", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        studyEventRepository.addAssignment(assignment);
        var resultSet = connection.createStatement().executeQuery("SELECT * FROM assignment WHERE title = 'Title'");
        assertTrue(resultSet.next());
        assertEquals("Description", resultSet.getString("description"));
    }

    @Test
    void addExam_addsNewExam() throws SQLException {
        Exam exam = new Exam(1, 1, "Math", OffsetDateTime.now(), 1);
        Address address = new Address(0, "Country", "City", "Street", 123, "PostalCode");
        studyEventRepository.addExam(exam, address);
        var resultSet = connection.createStatement().executeQuery("SELECT * FROM exam WHERE subject = 'Math'");
        assertTrue(resultSet.next());
        assertEquals(1, resultSet.getInt("study_plan_id"));
    }

    @Test
    void addClassSchedule_addsNewClassSchedule() throws SQLException {
        ClassSchedule classSchedule = new ClassSchedule(1, 1, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "WEEKLY");
        Address address = new Address(0, "Country", "City", "Street", 123, "PostalCode");
        studyEventRepository.addClassSchedule(classSchedule, address);
        var resultSet = connection.createStatement().executeQuery("SELECT * FROM class_schedule WHERE class_name = 'Math'");
        assertTrue(resultSet.next());
        assertEquals("Monday", resultSet.getString("day_of_week"));
    }

    @Test
    void getAssignmentsForDate_returnsAssignmentsForGivenDate() throws SQLException {
        OffsetDateTime dueDate = OffsetDateTime.now();
        connection.createStatement().execute("INSERT INTO assignment (study_plan_id, title, description, due_date, status) VALUES (1, 'Title', 'Description', '" + dueDate + "', 'PENDING')");
        List<Assignment> assignments = studyEventRepository.getAssignmentsForDate(dueDate.toLocalDate());

        assertEquals("Title", assignments.get(0).getTitle());
    }




    @Test
    void getExamsForDate_returnsEmptyListWhenNoExams() {
        List<Exam> exams = studyEventRepository.getExamsForDate(LocalDate.now());
        assertTrue(exams.isEmpty());
    }


    @Test
    void getClassesForDate_returnsEmptyListWhenNoClasses() {
        List<ClassSchedule> classSchedules = studyEventRepository.getClassesForDate(LocalDate.now());
        assertTrue(classSchedules.isEmpty());
    }


}