package org.example.personalizedstudyplanner.services;

import org.example.personalizedstudyplanner.context.UserContext;
import org.example.personalizedstudyplanner.database.DatabaseUtil;
import org.example.personalizedstudyplanner.models.StudyPlan;
import org.example.personalizedstudyplanner.services.StudyPlanService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class StudyPlanServiceTest {
    private StudyPlanService studyPlanService;

    @BeforeAll
    static void setUpUser() {
        UserContext.setCurrentUserId(1);
    }

    @BeforeEach
    void setUp() {
        studyPlanService = new StudyPlanService();
    }

    @Test
    void getAllStudyPlans() throws SQLException {
        List<StudyPlan> studyPlans = studyPlanService.getAllStudyPlans();
        assertNotNull(studyPlans);
    }

    @Test
    void createStudyPlan() throws SQLException {
        String title = "New Study Plan";
        String description = "Description of the new study plan";
        studyPlanService.createStudyPlan(title, description);
        List<StudyPlan> studyPlans = studyPlanService.getAllStudyPlans();
        assertTrue(studyPlans.stream().anyMatch(plan -> plan.getTitle().equals(title) && plan.getDescription().equals(description)));
    }

    @Test
    void deleteStudyPlan() throws SQLException {
        String title = "Study Plan to Delete";
        String description = "Description of the study plan to delete";
        studyPlanService.createStudyPlan(title, description);
        List<StudyPlan> studyPlans = studyPlanService.getAllStudyPlans();
        StudyPlan studyPlan = studyPlans.stream().filter(plan -> plan.getTitle().equals(title) && plan.getDescription().equals(description)).findFirst().orElse(null);
        assertNotNull(studyPlan);
        studyPlanService.deleteStudyPlan(studyPlan.getStudyPlanId());
        studyPlans = studyPlanService.getAllStudyPlans();
        assertFalse(studyPlans.stream().anyMatch(plan -> plan.getStudyPlanId() == studyPlan.getStudyPlanId()));
    }

    @Test
    void constructor_shouldThrowRuntimeException_whenDatabaseConnectionFails() {
        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            mockedDatabaseUtil.when(DatabaseUtil::connect).thenReturn(null);
            RuntimeException exception = assertThrows(RuntimeException.class, StudyPlanService::new);
            assertEquals("Failed to connect to the database", exception.getMessage());
        }
    }

    @Test
    void getAllStudyPlans_shouldRemovePlansNotBelongingToCurrentUser() throws SQLException {
        StudyPlan plan1 = new StudyPlan(1, 1, "User 1 Plan", "Description", null);
        StudyPlan plan2 = new StudyPlan(2, 2, "User 2 Plan", "Description", null);
        StudyPlan plan3 = new StudyPlan(3, 1, "User 1 Plan 2", "Description", null);

        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
            Connection mockConnection = Mockito.mock(Connection.class);
            mockedDatabaseUtil.when(DatabaseUtil::connect).thenReturn(mockConnection);

            StudyPlanService studyPlanService = new StudyPlanService() {
                @Override
                public List<StudyPlan> getAllStudyPlans() throws SQLException {
                    List<StudyPlan> studyPlans = new ArrayList<>(List.of(plan1, plan2, plan3));
                    int currentUserId = UserContext.getCurrentUserId();
                    studyPlans.removeIf(studyPlan -> studyPlan.getStudentId() != currentUserId);
                    return studyPlans;
                }
            };

            List<StudyPlan> studyPlans = studyPlanService.getAllStudyPlans();
            assertEquals(2, studyPlans.size());
            assertTrue(studyPlans.contains(plan1));
            assertTrue(studyPlans.contains(plan3));
            assertFalse(studyPlans.contains(plan2));
        }
    }
}