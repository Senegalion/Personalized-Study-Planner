package org.example.personalizedstudyplanner.models;

import java.time.OffsetDateTime;

public class Exam {
    private int examId;
    private int studyPlanId;
    private String subject;
    private OffsetDateTime date;
    private int addressId;
    private AssignmentStatus status;

    public Exam(int examId, int studyPlanId, String subject, OffsetDateTime date, int addressId, AssignmentStatus status) {
        this.examId = examId;
        this.studyPlanId = studyPlanId;
        this.subject = subject;
        this.date = date;
        this.addressId = addressId;
        this.status = status;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getStudyPlanId() {
        return studyPlanId;
    }

    public void setStudyPlanId(int studyPlanId) {
        this.studyPlanId = studyPlanId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }
}
