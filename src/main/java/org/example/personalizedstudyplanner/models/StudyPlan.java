package org.example.personalizedstudyplanner.models;

import java.time.OffsetDateTime;

public class StudyPlan {
    private int studyPlanId;
    private int studentId;
    private String title;
    private String description;
    private OffsetDateTime creationDate;

    public StudyPlan(int studyPlanId, int studentId, String title, String description, OffsetDateTime creationDate) {
        this.studyPlanId = studyPlanId;
        this.studentId = studentId;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
    }

    public int getStudyPlanId() {
        return studyPlanId;
    }

    public void setStudyPlanId(int studyPlanId) {
        this.studyPlanId = studyPlanId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
