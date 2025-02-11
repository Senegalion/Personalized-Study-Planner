package org.example.personalizedstudyplanner.models;

import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudyPlanTest {

    @Test
    void testStudyPlanConstructorAndGetters() {
        // Arrange
        int studyPlanId = 1;
        int studentId = 2;
        String title = "Spring Semester Plan";
        String description = "Study plan for spring semester courses";
        OffsetDateTime creationDate = OffsetDateTime.now(ZoneOffset.UTC);

        // Act
        StudyPlan studyPlan = new StudyPlan(studyPlanId, studentId, title, description, creationDate);

        // Assert
        assertEquals(studyPlanId, studyPlan.getStudyPlanId());
        assertEquals(studentId, studyPlan.getStudentId());
        assertEquals(title, studyPlan.getTitle());
        assertEquals(description, studyPlan.getDescription());
        assertEquals(creationDate, studyPlan.getCreationDate());
    }

    @Test
    void testStudyPlanSetters() {
        // Arrange
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

        // Act
        studyPlan.setStudyPlanId(newStudyPlanId);
        studyPlan.setStudentId(newStudentId);
        studyPlan.setTitle(newTitle);
        studyPlan.setDescription(newDescription);
        studyPlan.setCreationDate(newCreationDate);

        // Assert
        assertEquals(newStudyPlanId, studyPlan.getStudyPlanId());
        assertEquals(newStudentId, studyPlan.getStudentId());
        assertEquals(newTitle, studyPlan.getTitle());
        assertEquals(newDescription, studyPlan.getDescription());
        assertEquals(newCreationDate, studyPlan.getCreationDate());
    }

    @Test
    void testStudyPlanWithLongTitleAndDescription() {
        // Arrange
        String longTitle = "Comprehensive Study Plan for Computer Science Major with Specialization in Artificial Intelligence and Machine Learning";
        String longDescription = "This study plan covers all required courses for the Computer Science major, " +
                "including core subjects like Data Structures, Algorithms, and Database Systems, " +
                "as well as specialized courses in AI and ML such as Neural Networks, Deep Learning, " +
                "and Natural Language Processing. The plan also includes recommended practice exercises " +
                "and project deadlines.";

        // Act
        StudyPlan studyPlan = new StudyPlan(1, 1, longTitle, longDescription, OffsetDateTime.now(ZoneOffset.UTC));

        // Assert
        assertEquals(longTitle, studyPlan.getTitle());
        assertEquals(longDescription, studyPlan.getDescription());
    }

    @Test
    void testStudyPlanWithPastCreationDate() {
        // Arrange
        OffsetDateTime pastDate = OffsetDateTime.now(ZoneOffset.UTC).minusDays(30);

        // Act
        StudyPlan studyPlan = new StudyPlan(1, 1, "Past Plan", "A plan created in the past", pastDate);

        // Assert
        assertEquals(pastDate, studyPlan.getCreationDate());
    }
}