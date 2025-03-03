package org.example.personalizedstudyplanner.context.context;

import org.example.personalizedstudyplanner.context.StudyPlanContext;

import static org.junit.jupiter.api.Assertions.*;

class StudyPlanContextTest {
    @org.junit.jupiter.api.Test
    void testSetCurrentStudyPlanId() {
        StudyPlanContext.setCurrentStudyPlanId(1);
        assertEquals(1, StudyPlanContext.getCurrentStudyPlanId());
    }

    @org.junit.jupiter.api.Test
    void testGetCurrentStudyPlanId() {
        StudyPlanContext.setCurrentStudyPlanId(2);
        assertEquals(2, StudyPlanContext.getCurrentStudyPlanId());
    }

    @org.junit.jupiter.api.Test
    void testClear() {
        StudyPlanContext.setCurrentStudyPlanId(3);
        StudyPlanContext.clear();
        assertThrows(IllegalStateException.class, StudyPlanContext::getCurrentStudyPlanId);
    }
}
