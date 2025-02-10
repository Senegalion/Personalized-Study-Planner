package org.example.personalizedstudyplanner.models;

import java.time.OffsetDateTime;

public class Assignment {
    private int assignmentId;
    private int studyPlanId;
    private String title;
    private String description;
    private OffsetDateTime dueDate;
    private AssignmentStatus status;

    public Assignment(int assignmentId, int studyPlanId, String title, String description, OffsetDateTime dueDate, AssignmentStatus status) {
        this.assignmentId = assignmentId;
        this.studyPlanId = studyPlanId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getStudyPlanId() {
        return studyPlanId;
    }

    public void setStudyPlanId(int studyPlanId) {
        this.studyPlanId = studyPlanId;
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

    public OffsetDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(OffsetDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }
}
