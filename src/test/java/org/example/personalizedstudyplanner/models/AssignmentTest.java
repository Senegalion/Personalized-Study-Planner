package org.example.personalizedstudyplanner.models;

import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.AssignmentStatus;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignmentTest {
    @Test
    void getAssignmentId() {
        Assignment assignment = new Assignment(1, 101, "Title", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        assertEquals(1, assignment.getAssignmentId());
    }

    @Test
    void setAssignmentId() {
        Assignment assignment = new Assignment(1, 101, "Title", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        assignment.setAssignmentId(2);
        assertEquals(2, assignment.getAssignmentId());
    }

    @Test
    void getStudyPlanId() {
        Assignment assignment = new Assignment(1, 101, "Title", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        assertEquals(101, assignment.getStudyPlanId());
    }

    @Test
    void setStudyPlanId() {
        Assignment assignment = new Assignment(1, 101, "Title", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        assignment.setStudyPlanId(102);
        assertEquals(102, assignment.getStudyPlanId());
    }

    @Test
    void getTitle() {
        Assignment assignment = new Assignment(1, 101, "Title", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        assertEquals("Title", assignment.getTitle());
    }

    @Test
    void setTitle() {
        Assignment assignment = new Assignment(1, 101, "Title", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        assignment.setTitle("New Title");
        assertEquals("New Title", assignment.getTitle());
    }

    @Test
    void getDescription() {
        Assignment assignment = new Assignment(1, 101, "Title", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        assertEquals("Description", assignment.getDescription());
    }

    @Test
    void setDescription() {
        Assignment assignment = new Assignment(1, 101, "Title", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        assignment.setDescription("New Description");
        assertEquals("New Description", assignment.getDescription());
    }

    @Test
    void getDueDate() {
        OffsetDateTime dueDate = OffsetDateTime.now();
        Assignment assignment = new Assignment(1, 101, "Title", "Description", dueDate, AssignmentStatus.PENDING);
        assertEquals(dueDate, assignment.getDueDate());
    }

    @Test
    void setDueDate() {
        OffsetDateTime dueDate = OffsetDateTime.now();
        Assignment assignment = new Assignment(1, 101, "Title", "Description", dueDate, AssignmentStatus.PENDING);
        OffsetDateTime newDueDate = OffsetDateTime.now().plusDays(1);
        assignment.setDueDate(newDueDate);
        assertEquals(newDueDate, assignment.getDueDate());
    }

    @Test
    void getStatus() {
        Assignment assignment = new Assignment(1, 101, "Title", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        assertEquals(AssignmentStatus.PENDING, assignment.getStatus());
    }

    @Test
    void setStatus() {
        Assignment assignment = new Assignment(1, 101, "Title", "Description", OffsetDateTime.now(), AssignmentStatus.PENDING);
        assignment.setStatus(AssignmentStatus.COMPLETED);
        assertEquals(AssignmentStatus.COMPLETED, assignment.getStatus());
    }
}
