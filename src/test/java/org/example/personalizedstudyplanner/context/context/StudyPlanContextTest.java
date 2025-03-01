package org.example.personalizedstudyplanner.context.context;

import org.example.personalizedstudyplanner.context.StudyPlanContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudyPlanContextTest {
    @BeforeEach
    void setUp() {
        StudyPlanContext.clear();
    }

    @Test
    void testSetAndGetCurrentStudyPlan() {
        StudyPlanContext.setCurrentStudyPlanId(1);
        assertEquals(1, StudyPlanContext.getCurrentStudyPlanId());
    }

    @Test
    void testGetCurrentUserIdWhenNotSet() {
        Exception exception = assertThrows(IllegalStateException.class, StudyPlanContext::getCurrentStudyPlanId);
        assertEquals("No currently selected study plan", exception.getMessage());
    }

    @Test
    void testClear() {
        StudyPlanContext.setCurrentStudyPlanId(1);
        StudyPlanContext.clear();
        Exception exception = assertThrows(IllegalStateException.class, StudyPlanContext::getCurrentStudyPlanId);
        assertEquals("No currently selected study plan", exception.getMessage());
    }
}
