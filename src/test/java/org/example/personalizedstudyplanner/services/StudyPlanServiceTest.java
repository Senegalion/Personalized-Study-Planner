package org.example.personalizedstudyplanner.services;

import org.example.personalizedstudyplanner.context.UserContext;
import org.example.personalizedstudyplanner.models.StudyPlan;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}