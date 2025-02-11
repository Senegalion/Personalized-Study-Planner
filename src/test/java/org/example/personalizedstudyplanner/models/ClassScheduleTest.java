package org.example.personalizedstudyplanner.models;

import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassScheduleTest {

    @Test
    void testClassScheduleConstructorAndGetters() {
        // Arrange
        int classScheduleId = 1;
        int studyPlanId = 2;
        String dayOfWeek = "Monday";
        String className = "Mathematics";
        OffsetDateTime startTime = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime endTime = startTime.plusHours(2);
        int addressId = 3;
        String recurrencePattern = "WEEKLY";

        // Act
        ClassSchedule classSchedule = new ClassSchedule(
                classScheduleId,
                studyPlanId,
                dayOfWeek,
                className,
                startTime,
                endTime,
                addressId,
                recurrencePattern
        );

        // Assert
        assertEquals(classScheduleId, classSchedule.getClassScheduleId());
        assertEquals(studyPlanId, classSchedule.getStudyPlanId());
        assertEquals(dayOfWeek, classSchedule.getDayOfWeek());
        assertEquals(className, classSchedule.getClassName());
        assertEquals(startTime, classSchedule.getStartTime());
        assertEquals(endTime, classSchedule.getEndTime());
        assertEquals(addressId, classSchedule.getAddressId());
        assertEquals(recurrencePattern, classSchedule.getRecurrencePattern());
    }

    @Test
    void testClassScheduleSetters() {
        // Arrange
        ClassSchedule classSchedule = new ClassSchedule(
                1,
                1,
                "Monday",
                "Initial Class",
                OffsetDateTime.now(ZoneOffset.UTC),
                OffsetDateTime.now(ZoneOffset.UTC).plusHours(1),
                1,
                "WEEKLY"
        );

        int newClassScheduleId = 2;
        int newStudyPlanId = 3;
        String newDayOfWeek = "Tuesday";
        String newClassName = "Updated Class";
        OffsetDateTime newStartTime = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1);
        OffsetDateTime newEndTime = newStartTime.plusHours(2);
        int newAddressId = 4;
        String newRecurrencePattern = "MONTHLY";

        // Act
        classSchedule.setClassScheduleId(newClassScheduleId);
        classSchedule.setStudyPlanId(newStudyPlanId);
        classSchedule.setDayOfWeek(newDayOfWeek);
        classSchedule.setClassName(newClassName);
        classSchedule.setStartTime(newStartTime);
        classSchedule.setEndTime(newEndTime);
        classSchedule.setAddressId(newAddressId);
        classSchedule.setRecurrencePattern(newRecurrencePattern);

        // Assert
        assertEquals(newClassScheduleId, classSchedule.getClassScheduleId());
        assertEquals(newStudyPlanId, classSchedule.getStudyPlanId());
        assertEquals(newDayOfWeek, classSchedule.getDayOfWeek());
        assertEquals(newClassName, classSchedule.getClassName());
        assertEquals(newStartTime, classSchedule.getStartTime());
        assertEquals(newEndTime, classSchedule.getEndTime());
        assertEquals(newAddressId, classSchedule.getAddressId());
        assertEquals(newRecurrencePattern, classSchedule.getRecurrencePattern());
    }

    @Test
    void testTimeValidation() {
        // Arrange
        OffsetDateTime startTime = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime endTime = startTime.plusHours(2);

        // Act
        ClassSchedule classSchedule = new ClassSchedule(
                1,
                1,
                "Monday",
                "Test Class",
                startTime,
                endTime,
                1,
                "WEEKLY"
        );

        // Assert
        assertEquals(startTime, classSchedule.getStartTime());
        assertEquals(endTime, classSchedule.getEndTime());
        assertTrue(classSchedule.getEndTime().isAfter(classSchedule.getStartTime()));
    }

    @Test
    void testRecurrencePatternValidation() {
        // Test different valid recurrence patterns
        String[] validPatterns = {"DAILY", "WEEKLY", "MONTHLY", "NONE"};

        for (String pattern : validPatterns) {
            // Arrange & Act
            ClassSchedule classSchedule = new ClassSchedule(
                    1,
                    1,
                    "Monday",
                    "Test Class",
                    OffsetDateTime.now(ZoneOffset.UTC),
                    OffsetDateTime.now(ZoneOffset.UTC).plusHours(1),
                    1,
                    pattern
            );

            // Assert
            assertEquals(pattern, classSchedule.getRecurrencePattern());
        }
    }
}