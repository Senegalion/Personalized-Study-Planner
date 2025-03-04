package org.example.personalizedstudyplanner.models;

import org.example.personalizedstudyplanner.models.AssignmentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignmentStatusTest {
    @Test
    void values_returnsAllEnumValues() {
        AssignmentStatus[] expectedValues = {AssignmentStatus.PENDING, AssignmentStatus.COMPLETED, AssignmentStatus.OVERDUE};
        assertArrayEquals(expectedValues, AssignmentStatus.values());
    }

    @Test
    void valueOf_returnsCorrectEnumValue() {
        assertEquals(AssignmentStatus.PENDING, AssignmentStatus.valueOf("PENDING"));
        assertEquals(AssignmentStatus.COMPLETED, AssignmentStatus.valueOf("COMPLETED"));
        assertEquals(AssignmentStatus.OVERDUE, AssignmentStatus.valueOf("OVERDUE"));
    }

    @Test
    void getProgressValue_returnsCorrectProgressValue() {
        assertEquals(0.0, AssignmentStatus.PENDING.getProgressValue());
        assertEquals(1.0, AssignmentStatus.COMPLETED.getProgressValue());
        assertEquals(0.5, AssignmentStatus.OVERDUE.getProgressValue());
    }
}
