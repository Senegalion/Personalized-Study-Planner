package org.example.personalizedstudyplanner.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {
    private Assignment assignment;
    private final int TEST_ASSIGNMENT_ID = 1;
    private final int TEST_STUDY_PLAN_ID = 1;
    private final String TEST_TITLE = "Test Assignment";
    private final String TEST_DESCRIPTION = "Test Description";
    private final OffsetDateTime TEST_DUE_DATE = OffsetDateTime.of(2024, 12, 31, 23, 59, 59, 0, ZoneOffset.UTC);
    private final AssignmentStatus TEST_STATUS = AssignmentStatus.PENDING;

    @BeforeEach
    void setUp() {
        assignment = new Assignment(
                TEST_ASSIGNMENT_ID,
                TEST_STUDY_PLAN_ID,
                TEST_TITLE,
                TEST_DESCRIPTION,
                TEST_DUE_DATE,
                TEST_STATUS
        );
    }

    @Test
    void testAssignmentConstructor() {
        assertNotNull(assignment);
        assertEquals(TEST_ASSIGNMENT_ID, assignment.getAssignmentId());
        assertEquals(TEST_STUDY_PLAN_ID, assignment.getStudyPlanId());
        assertEquals(TEST_TITLE, assignment.getTitle());
        assertEquals(TEST_DESCRIPTION, assignment.getDescription());
        assertEquals(TEST_DUE_DATE, assignment.getDueDate());
        assertEquals(TEST_STATUS, assignment.getStatus());
    }

    @Test
    void testSetAssignmentId() {
        int newId = 2;
        assignment.setAssignmentId(newId);
        assertEquals(newId, assignment.getAssignmentId());
    }

    @Test
    void testSetStudyPlanId() {
        int newStudyPlanId = 2;
        assignment.setStudyPlanId(newStudyPlanId);
        assertEquals(newStudyPlanId, assignment.getStudyPlanId());
    }

    @Test
    void testSetTitle() {
        String newTitle = "Updated Title";
        assignment.setTitle(newTitle);
        assertEquals(newTitle, assignment.getTitle());
    }

    @Test
    void testSetDescription() {
        String newDescription = "Updated Description";
        assignment.setDescription(newDescription);
        assertEquals(newDescription, assignment.getDescription());
    }

    @Test
    void testSetDueDate() {
        OffsetDateTime newDueDate = OffsetDateTime.now(ZoneOffset.UTC);
        assignment.setDueDate(newDueDate);
        assertEquals(newDueDate, assignment.getDueDate());
    }

    @Test
    void testSetStatus() {
        AssignmentStatus newStatus = AssignmentStatus.COMPLETED;
        assignment.setStatus(newStatus);
        assertEquals(newStatus, assignment.getStatus());
    }
}