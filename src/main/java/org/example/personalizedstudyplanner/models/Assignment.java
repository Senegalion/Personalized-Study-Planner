package org.example.personalizedstudyplanner.models;

import org.example.personalizedstudyplanner.context.LocaleContext;

import java.time.OffsetDateTime;
import java.util.Locale;

public class Assignment {
    private int assignmentId;
    private int studyPlanId;
    private String title;
    private String description;
    private OffsetDateTime dueDate;
    private AssignmentStatus statusEn;
    private AssignmentStatus statusPl;

    public Assignment(int assignmentId, int studyPlanId, String title, String description, OffsetDateTime dueDate, AssignmentStatus statusEn, AssignmentStatus statusPl) {
        this.assignmentId = assignmentId;
        this.studyPlanId = studyPlanId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.statusEn = statusEn;
        this.statusPl = statusPl;
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
        Locale currentLocale = LocaleContext.getCurrentLocale();
        return currentLocale.equals(new Locale("pl", "PL")) ? statusPl : statusEn;
    }

    public void setStatus(AssignmentStatus status) {
        Locale currentLocale = LocaleContext.getCurrentLocale();
        if (currentLocale.equals(new Locale("pl", "PL"))) {
            this.statusPl = status;
        } else {
            this.statusEn = status;
        }
    }

    public AssignmentStatus getStatusEn() {
        return statusEn;
    }

    public void setStatusEn(AssignmentStatus statusEn) {
        this.statusEn = statusEn;
    }

    public AssignmentStatus getStatusPl() {
        return statusPl;
    }

    public void setStatusPl(AssignmentStatus statusPl) {
        this.statusPl = statusPl;
    }
}
