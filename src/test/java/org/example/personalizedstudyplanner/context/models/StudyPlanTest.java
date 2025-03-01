package org.example.personalizedstudyplanner.context.models;

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
        String title = "Spring Semester Plan";
        String description = "Study plan for spring semester courses";
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
        StudyPlan studyPlan = new StudyPlan(
                1,
                1,
                "Initial Title",
                "Initial Description",
                OffsetDateTime.now(ZoneOffset.UTC)
        );

        int newStudyPlanId = 2;
        int newStudentId = 3;
        String newTitle = "Updated Study Plan";
        String newDescription = "Updated description for the study plan";
        OffsetDateTime newCreationDate = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1);

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

    @Test
    void testStudyPlanWithLongTitleAndDescription() {
        String longTitle = "Comprehensive Study Plan for Computer Science Major with Specialization in Artificial Intelligence and Machine Learning";
        String longDescription = "This study plan covers all required courses for the Computer Science major, " +
                "including core subjects like Data Structures, Algorithms, and Database Systems, " +
                "as well as specialized courses in AI and ML such as Neural Networks, Deep Learning, " +
                "and Natural Language Processing. The plan also includes recommended practice exercises " +
                "and project deadlines.";

        StudyPlan studyPlan = new StudyPlan(1, 1, longTitle, longDescription, OffsetDateTime.now(ZoneOffset.UTC));

        assertEquals(longTitle, studyPlan.getTitle());
        assertEquals(longDescription, studyPlan.getDescription());
    }

    @Test
    void testStudyPlanWithPastCreationDate() {
        OffsetDateTime pastDate = OffsetDateTime.now(ZoneOffset.UTC).minusDays(30);

        StudyPlan studyPlan = new StudyPlan(1, 1, "Past Plan", "A plan created in the past", pastDate);

        assertEquals(pastDate, studyPlan.getCreationDate());
    }
}
