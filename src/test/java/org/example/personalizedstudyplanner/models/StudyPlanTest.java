package org.example.personalizedstudyplanner.models;

import org.example.personalizedstudyplanner.models.StudyPlan;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudyPlanTest {
    @Test
    void testStudyPlanConstructorAndGetters() {
        int studyPlanId = 1;
        int studentId = 2;
        String title = "Test Plan";
        String description = "Test Description";
        OffsetDateTime creationDate = OffsetDateTime.now(ZoneOffset.UTC);
        StudyPlan studyPlan = new StudyPlan(studyPlanId, studentId, title, description, creationDate);
        assertEquals(studyPlanId, studyPlan.getStudyPlanId());
        assertEquals(studentId, studyPlan.getStudentId());
        assertEquals(title, studyPlan.getTitle());
        assertEquals(description, studyPlan.getDescription());
        assertEquals(creationDate, studyPlan.getCreationDate());
    }

    @Test
    void testStudyPlanSetters() {
        StudyPlan studyPlan = new StudyPlan(1, 2, "Initial Title", "Initial Description",
                OffsetDateTime.now(ZoneOffset.UTC));
        int newStudyPlanId = 3;
        int newStudentId = 4;
        String newTitle = "New Title";
        String newDescription = "New Description";
        OffsetDateTime newCreationDate = OffsetDateTime.now(ZoneOffset.UTC);
        studyPlan.setStudyPlanId(newStudyPlanId);
        studyPlan.setStudentId(newStudentId);
        studyPlan.setTitle(newTitle);
        studyPlan.setDescription(newDescription);
        studyPlan.setCreationDate(newCreationDate);
        assertEquals(newStudyPlanId, studyPlan.getStudyPlanId());
        assertEquals(newStudentId, studyPlan.getStudentId());
        assertEquals(newTitle, studyPlan.getTitle());
        assertEquals(newDescription, studyPlan.getDescription());
        assertEquals(newCreationDate, studyPlan.getCreationDate());
    }
}
