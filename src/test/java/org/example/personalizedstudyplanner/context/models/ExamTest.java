package org.example.personalizedstudyplanner.context.models;

import org.example.personalizedstudyplanner.models.AssignmentStatus;
import org.example.personalizedstudyplanner.models.Exam;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExamTest {
    @Test
    void getExamId() {
        Exam exam = new Exam(1, 101, "Math", OffsetDateTime.now(), 1, AssignmentStatus.PENDING);
        assertEquals(1, exam.getExamId());
    }

    @Test
    void setExamId() {
        Exam exam = new Exam(1, 101, "Math", OffsetDateTime.now(), 1, AssignmentStatus.PENDING);
        exam.setExamId(2);
        assertEquals(2, exam.getExamId());
    }

    @Test
    void getStudyPlanId() {
        Exam exam = new Exam(1, 101, "Math", OffsetDateTime.now(), 1, AssignmentStatus.PENDING);
        assertEquals(101, exam.getStudyPlanId());
    }

    @Test
    void setStudyPlanId() {
        Exam exam = new Exam(1, 101, "Math", OffsetDateTime.now(), 1, AssignmentStatus.PENDING);
        exam.setStudyPlanId(102);
        assertEquals(102, exam.getStudyPlanId());
    }

    @Test
    void getSubject() {
        Exam exam = new Exam(1, 101, "Math", OffsetDateTime.now(), 1, AssignmentStatus.PENDING);
        assertEquals("Math", exam.getSubject());
    }

    @Test
    void setSubject() {
        Exam exam = new Exam(1, 101, "Math", OffsetDateTime.now(), 1, AssignmentStatus.PENDING);
        exam.setSubject("Science");
        assertEquals("Science", exam.getSubject());
    }

    @Test
    void getDate() {
        OffsetDateTime date = OffsetDateTime.now();
        Exam exam = new Exam(1, 101, "Math", date, 1, AssignmentStatus.PENDING);
        assertEquals(date, exam.getDate());
    }

    @Test
    void setDate() {
        OffsetDateTime date = OffsetDateTime.now();
        Exam exam = new Exam(1, 101, "Math", date, 1, AssignmentStatus.PENDING);
        OffsetDateTime newDate = OffsetDateTime.now().plusDays(1);
        exam.setDate(newDate);
        assertEquals(newDate, exam.getDate());
    }

    @Test
    void getAddressId() {
        Exam exam = new Exam(1, 101, "Math", OffsetDateTime.now(), 1, AssignmentStatus.PENDING);
        assertEquals(1, exam.getAddressId());
    }

    @Test
    void setAddressId() {
        Exam exam = new Exam(1, 101, "Math", OffsetDateTime.now(), 1, AssignmentStatus.PENDING);
        exam.setAddressId(2);
        assertEquals(2, exam.getAddressId());
    }
}
