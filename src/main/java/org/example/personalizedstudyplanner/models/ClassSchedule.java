package org.example.personalizedstudyplanner.models;

import java.time.OffsetDateTime;

public class ClassSchedule {
    private int classScheduleId;
    private int studyPlanId;
    private String dayOfWeek;
    private String className;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private int addressId;
    private String recurrencePattern;

    public ClassSchedule(int classScheduleId, int studyPlanId, String dayOfWeek, String className, OffsetDateTime startTime, OffsetDateTime endTime, int addressId, String recurrencePattern) {
        this.classScheduleId = classScheduleId;
        this.studyPlanId = studyPlanId;
        this.dayOfWeek = dayOfWeek;
        this.className = className;
        this.startTime = startTime;
        this.endTime = endTime;
        this.addressId = addressId;
        this.recurrencePattern = recurrencePattern;
    }

    public int getClassScheduleId() {
        return classScheduleId;
    }

    public void setClassScheduleId(int classScheduleId) {
        this.classScheduleId = classScheduleId;
    }

    public int getStudyPlanId() {
        return studyPlanId;
    }

    public void setStudyPlanId(int studyPlanId) {
        this.studyPlanId = studyPlanId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getRecurrencePattern() {
        return recurrencePattern;
    }

    public void setRecurrencePattern(String recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }
}
