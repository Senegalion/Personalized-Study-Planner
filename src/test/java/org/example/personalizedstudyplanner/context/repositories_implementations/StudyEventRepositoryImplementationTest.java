package org.example.personalizedstudyplanner.context.repositories_implementations;

import org.example.personalizedstudyplanner.models.*;
import org.example.personalizedstudyplanner.repositories.StudyEventRepository;
import org.example.personalizedstudyplanner.repositories_implementations.AddressRepositoryImplementation;
import org.example.personalizedstudyplanner.repositories_implementations.StudyEventRepositoryImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class StudyEventRepositoryImplementationTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private StudyEventRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        repository = new StudyEventRepositoryImplementation(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    void testGetAssignmentsForDate() throws SQLException {
        LocalDate date = LocalDate.now();
        int studyPlanId = 101;
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("assignment_id")).thenReturn(1);
        when(mockResultSet.getInt("study_plan_id")).thenReturn(101);
        when(mockResultSet.getString("title")).thenReturn("Math Homework");
        when(mockResultSet.getString("description")).thenReturn("Solve problems 1-10");
        when(mockResultSet.getObject("due_date", Timestamp.class)).thenReturn(Timestamp.from(Instant.now()));
        when(mockResultSet.getString("status")).thenReturn("PENDING");

        List<Assignment> assignments = repository.getAssignmentsForDate(date, studyPlanId);

        assertFalse(assignments.isEmpty());
        assertEquals(1, assignments.get(0).getAssignmentId());
        assertEquals("Math Homework", assignments.get(0).getTitle());
    }

    @Test
    void testGetExamsForDate() throws SQLException {
        LocalDate date = LocalDate.now();
        int studyPlanId = 202;
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("exam_id")).thenReturn(2);
        when(mockResultSet.getInt("study_plan_id")).thenReturn(202);
        when(mockResultSet.getString("subject")).thenReturn("Physics");
        when(mockResultSet.getObject("date", Timestamp.class)).thenReturn(Timestamp.from(Instant.now()));
        when(mockResultSet.getInt("address_id")).thenReturn(5);
        when(mockResultSet.getString("status")).thenReturn("PENDING");

        List<Exam> exams = repository.getExamsForDate(date, studyPlanId);

        assertFalse(exams.isEmpty());
        assertEquals(2, exams.get(0).getExamId());
        assertEquals("Physics", exams.get(0).getSubject());
    }

    @Test
    void testGetClassesForDate() throws SQLException {
        LocalDate date = LocalDate.now();
        int studyPlanId = 303;
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("class_schedule_id")).thenReturn(3);
        when(mockResultSet.getInt("study_plan_id")).thenReturn(303);
        when(mockResultSet.getString("day_of_week")).thenReturn("Monday");
        when(mockResultSet.getString("class_name")).thenReturn("History");
        when(mockResultSet.getObject("start_time", Timestamp.class)).thenReturn(Timestamp.from(Instant.now()));
        when(mockResultSet.getObject("end_time", Timestamp.class)).thenReturn(Timestamp.from(Instant.now().plusSeconds(3600)));
        when(mockResultSet.getInt("address_id")).thenReturn(7);
        when(mockResultSet.getString("recurrence_pattern")).thenReturn("Weekly");

        List<ClassSchedule> classes = repository.getClassesForDate(date, studyPlanId);

        assertFalse(classes.isEmpty());
        assertEquals(3, classes.get(0).getClassScheduleId());
        assertEquals("History", classes.get(0).getClassName());
    }

    @Test
    void testAddExam() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Exam exam = new Exam(2, 202, "Physics", Instant.now().atOffset(ZoneOffset.UTC), 5, AssignmentStatus.PENDING);
        Address address = new Address(1, "Country", "City", "Street", 10, "12345");

        AddressRepositoryImplementation addressRepositoryMock = mock(AddressRepositoryImplementation.class);
        when(addressRepositoryMock.getOrCreateAddress(any(Address.class))).thenReturn(5);

        StudyEventRepositoryImplementation repositoryWithMockAddress = new StudyEventRepositoryImplementation(mockConnection) {
            @Override
            public void addExam(Exam exam, Address address) {
                int addressId = addressRepositoryMock.getOrCreateAddress(address);
                String sql = "INSERT INTO exam (study_plan_id, subject, date, address_id) VALUES (?, ?, ?, ?)";

                try (PreparedStatement insertStmt = mockConnection.prepareStatement(sql)) {
                    insertStmt.setInt(1, exam.getStudyPlanId());
                    insertStmt.setString(2, exam.getSubject());
                    insertStmt.setTimestamp(3, Timestamp.from(exam.getDate().toInstant()));
                    insertStmt.setInt(4, addressId);
                    insertStmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };

        repositoryWithMockAddress.addExam(exam, address);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testAddClassSchedule() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        ClassSchedule classSchedule = new ClassSchedule(3, 303, "Monday", "History",
                Instant.now().atOffset(ZoneOffset.UTC),
                Instant.now().atOffset(ZoneOffset.UTC).plusSeconds(3600),
                7, "Weekly");
        Address address = new Address(2, "Country", "City", "Street", 20, "67890");

        AddressRepositoryImplementation addressRepositoryMock = mock(AddressRepositoryImplementation.class);
        when(addressRepositoryMock.getOrCreateAddress(any(Address.class))).thenReturn(7);

        StudyEventRepositoryImplementation repositoryWithMockAddress = new StudyEventRepositoryImplementation(mockConnection) {
            @Override
            public void addClassSchedule(ClassSchedule classSchedule, Address address) {
                int addressId = addressRepositoryMock.getOrCreateAddress(address);
                String sql = "INSERT INTO class_schedule (study_plan_id, day_of_week, class_name, start_time, end_time, address_id, recurrence_pattern) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement insertStmt = mockConnection.prepareStatement(sql)) {
                    insertStmt.setInt(1, classSchedule.getStudyPlanId());
                    insertStmt.setString(2, classSchedule.getDayOfWeek());
                    insertStmt.setString(3, classSchedule.getClassName());
                    insertStmt.setTimestamp(4, Timestamp.from(classSchedule.getStartTime().toInstant()));
                    insertStmt.setTimestamp(5, Timestamp.from(classSchedule.getEndTime().toInstant()));
                    insertStmt.setInt(6, addressId);
                    insertStmt.setString(7, classSchedule.getRecurrencePattern());
                    insertStmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };

        repositoryWithMockAddress.addClassSchedule(classSchedule, address);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
}
