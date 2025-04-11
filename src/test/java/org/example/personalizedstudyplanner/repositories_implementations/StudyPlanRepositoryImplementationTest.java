//package org.example.personalizedstudyplanner.repositories_implementations;
//
//import org.example.personalizedstudyplanner.models.StudyPlan;
//import org.example.personalizedstudyplanner.repositories_implementations.StudyPlanRepositoryImplementation;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.sql.*;
//import java.time.OffsetDateTime;
//import java.time.ZoneOffset;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//class StudyPlanRepositoryImplementationTest {
//
//    @Mock
//    private Connection connection;
//
//    @Mock
//    private Statement statement;
//
//    @Mock
//    private PreparedStatement preparedStatement;
//
//    @Mock
//    private ResultSet resultSet;
//
//    @InjectMocks
//    private StudyPlanRepositoryImplementation repository;
//
//    @Mock
//    private PreparedStatement deleteAssignmentsStmt;
//
//    @Mock
//    private PreparedStatement deleteExamsStmt;
//
//    @Mock
//    private PreparedStatement deleteSchedulesStmt;
//
//    @Mock
//    private PreparedStatement deleteStudyPlanStmt;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        MockitoAnnotations.openMocks(this);
//        when(connection.createStatement()).thenReturn(statement);
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
//        when(connection.prepareStatement("DELETE FROM assignment WHERE study_plan_id = ?")).thenReturn(deleteAssignmentsStmt);
//        when(connection.prepareStatement("DELETE FROM exam WHERE study_plan_id = ?")).thenReturn(deleteExamsStmt);
//        when(connection.prepareStatement("DELETE FROM class_schedule WHERE study_plan_id = ?")).thenReturn(deleteSchedulesStmt);
//        when(connection.prepareStatement("DELETE FROM study_plan WHERE study_plan_id = ? AND student_id = ?")).thenReturn(deleteStudyPlanStmt);
//    }
//
//    @Test
//    void testFindAllStudyPlans() throws Exception {
//        String query = "SELECT * FROM study_plan";
//        when(statement.executeQuery(query)).thenReturn(resultSet);
//
//        when(resultSet.next()).thenReturn(true).thenReturn(false);
//        when(resultSet.getInt("study_plan_id")).thenReturn(1);
//        when(resultSet.getInt("student_id")).thenReturn(1);
//        when(resultSet.getString("title")).thenReturn("Test Title");
//        when(resultSet.getString("description")).thenReturn("Test Description");
//        when(resultSet.getTimestamp("creation_date")).thenReturn(Timestamp.from(OffsetDateTime.of(2022, 12, 31, 22, 0, 0, 0, ZoneOffset.UTC).toInstant()));
//
//        List<StudyPlan> studyPlans = repository.findAllStudyPlans();
//
//        assertNotNull(studyPlans);
//        assertEquals(1, studyPlans.size());
//        StudyPlan studyPlan = studyPlans.get(0);
//        assertEquals(1, studyPlan.getStudyPlanId());
//        assertEquals(1, studyPlan.getStudentId());
//        assertEquals("Test Title", studyPlan.getTitle());
//        assertEquals("Test Description", studyPlan.getDescription());
//
//        OffsetDateTime expectedDate = OffsetDateTime.of(2022, 12, 31, 22, 0, 0, 0, ZoneOffset.UTC);
//        OffsetDateTime actualDate = studyPlan.getCreationDate();
//        assertEquals(expectedDate, actualDate);
//
//        verify(statement).executeQuery(query);
//        verify(resultSet, times(2)).next();
//    }
//
//    @Test
//    void testSave() throws Exception {
//        String query = "INSERT INTO study_plan (student_id, title, description, creation_date) VALUES (?, ?, ?, ?)";
//        StudyPlan studyPlan = new StudyPlan(1, 1, "Test Title", "Test Description", OffsetDateTime.now(ZoneOffset.UTC));
//
//        repository.save(studyPlan);
//
//        verify(preparedStatement).setInt(1, studyPlan.getStudentId());
//        verify(preparedStatement).setString(2, studyPlan.getTitle());
//        verify(preparedStatement).setString(3, studyPlan.getDescription());
//        verify(preparedStatement).setObject(4, studyPlan.getCreationDate());
//        verify(preparedStatement).executeUpdate();
//    }
//
//    @Test
//    void testDelete() throws Exception {
//        String query = "DELETE FROM study_plan WHERE study_plan_id = ? AND student_id = ?";
//        int studyPlanId = 1;
//        int currentUserId = 1;
//
//        repository.delete(studyPlanId, currentUserId);
//
//        verify(deleteAssignmentsStmt, times(1)).setInt(1, studyPlanId);
//        verify(deleteAssignmentsStmt, times(1)).executeUpdate();
//
//        verify(deleteExamsStmt, times(1)).setInt(1, studyPlanId);
//        verify(deleteExamsStmt, times(1)).executeUpdate();
//
//        verify(deleteSchedulesStmt, times(1)).setInt(1, studyPlanId);
//        verify(deleteSchedulesStmt, times(1)).executeUpdate();
//
//        verify(deleteStudyPlanStmt, times(1)).setInt(1, studyPlanId);
//        verify(deleteStudyPlanStmt, times(1)).setInt(2, currentUserId);
//        verify(deleteStudyPlanStmt, times(1)).executeUpdate();
//
//    }
//}
