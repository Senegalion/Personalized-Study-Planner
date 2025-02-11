package org.example.personalizedstudyplanner.services;

import org.example.personalizedstudyplanner.context.UserContext;
import org.example.personalizedstudyplanner.models.StudyPlan;
import org.example.personalizedstudyplanner.repositories.StudyPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyPlanServiceTest {

    @Mock
    private StudyPlanRepository studyPlanRepository;

    private StudyPlanService studyPlanService;

    private final int mockUserId = 1;


    @BeforeEach
    void setUp() {
        studyPlanService = new StudyPlanService(studyPlanRepository);
        Mockito.reset(studyPlanRepository); // Clears previous interactions
    }


    @Test
    void testGetAllStudyPlans() throws SQLException {
        try (MockedStatic<UserContext> mockedUserContext = Mockito.mockStatic(UserContext.class)) {
            mockedUserContext.when(UserContext::getCurrentUserId).thenReturn(mockUserId);

            List<StudyPlan> mockStudyPlans = new ArrayList<>();
            mockStudyPlans.add(new StudyPlan(1, mockUserId, "Math Plan", "Algebra and Geometry", OffsetDateTime.now()));
            mockStudyPlans.add(new StudyPlan(2, 2, "Science Plan", "Physics and Chemistry", OffsetDateTime.now()));

            when(studyPlanRepository.findAllStudyPlans()).thenReturn(mockStudyPlans);

            List<StudyPlan> result = studyPlanService.getAllStudyPlans();

            assertEquals(1, result.size());
            assertEquals("Math Plan", result.get(0).getTitle());
        }
    }


    @Test
    void testCreateStudyPlan() throws SQLException {
        try (MockedStatic<UserContext> mockedUserContext = Mockito.mockStatic(UserContext.class)) {
            mockedUserContext.when(UserContext::getCurrentUserId).thenReturn(mockUserId);

            String title = "New Study Plan";
            String description = "Description of the study plan";

            studyPlanService.createStudyPlan(title, description);

            verify(studyPlanRepository, times(1)).save(any(StudyPlan.class));
        }
    }


    @Test
    void testDeleteStudyPlan() throws SQLException {
        int studyPlanId = 1;

        try (MockedStatic<UserContext> mockedUserContext = Mockito.mockStatic(UserContext.class)) {
            mockedUserContext.when(UserContext::getCurrentUserId).thenReturn(mockUserId);

            studyPlanService.deleteStudyPlan(studyPlanId);

            verify(studyPlanRepository, times(1)).delete(studyPlanId, mockUserId);
        }
    }

}