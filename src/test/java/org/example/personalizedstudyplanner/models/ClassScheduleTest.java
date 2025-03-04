package org.example.personalizedstudyplanner.models;

import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import static org.junit.jupiter.api.Assertions.*;
class ClassScheduleTest {
    @Test
    void getClassScheduleId() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        assertEquals(1, classSchedule.getClassScheduleId());
    }
    @Test
    void setClassScheduleId() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        classSchedule.setClassScheduleId(2);
        assertEquals(2, classSchedule.getClassScheduleId());
    }
    @Test
    void getStudyPlanId() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        assertEquals(101, classSchedule.getStudyPlanId());
    }
    @Test
    void setStudyPlanId() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        classSchedule.setStudyPlanId(102);
        assertEquals(102, classSchedule.getStudyPlanId());
    }
    @Test
    void getDayOfWeek() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        assertEquals("Monday", classSchedule.getDayOfWeek());
    }
    @Test
    void setDayOfWeek() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        classSchedule.setDayOfWeek("Tuesday");
        assertEquals("Tuesday", classSchedule.getDayOfWeek());
    }
    @Test
    void getClassName() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        assertEquals("Math", classSchedule.getClassName());
    }
    @Test
    void setClassName() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        classSchedule.setClassName("Science");
        assertEquals("Science", classSchedule.getClassName());
    }
    @Test
    void getStartTime() {
        OffsetDateTime startTime = OffsetDateTime.now();
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", startTime, OffsetDateTime.now().plusHours(1), 1, "Weekly");
        assertEquals(startTime, classSchedule.getStartTime());
    }
    @Test
    void setStartTime() {
        OffsetDateTime startTime = OffsetDateTime.now();
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", startTime, OffsetDateTime.now().plusHours(1), 1, "Weekly");
        OffsetDateTime newStartTime = OffsetDateTime.now().plusDays(1);
        classSchedule.setStartTime(newStartTime);
        assertEquals(newStartTime, classSchedule.getStartTime());
    }
    @Test
    void getEndTime() {
        OffsetDateTime endTime = OffsetDateTime.now().plusHours(1);
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), endTime, 1, "Weekly");
        assertEquals(endTime, classSchedule.getEndTime());
    }
    @Test
    void setEndTime() {
        OffsetDateTime endTime = OffsetDateTime.now().plusHours(1);
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), endTime, 1, "Weekly");
        OffsetDateTime newEndTime = OffsetDateTime.now().plusDays(1).plusHours(1);
        classSchedule.setEndTime(newEndTime);
        assertEquals(newEndTime, classSchedule.getEndTime());
    }
    @Test
    void getAddressId() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        assertEquals(1, classSchedule.getAddressId());
    }
    @Test
    void setAddressId() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        classSchedule.setAddressId(2);
        assertEquals(2, classSchedule.getAddressId());
    }
    @Test
    void getRecurrencePattern() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        assertEquals("Weekly", classSchedule.getRecurrencePattern());
    }
    @Test
    void setRecurrencePattern() {
        ClassSchedule classSchedule = new ClassSchedule(1, 101, "Monday", "Math", OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), 1, "Weekly");
        classSchedule.setRecurrencePattern("Monthly");
        assertEquals("Monthly", classSchedule.getRecurrencePattern());
    }
}
