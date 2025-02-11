package org.example.personalizedstudyplanner.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}