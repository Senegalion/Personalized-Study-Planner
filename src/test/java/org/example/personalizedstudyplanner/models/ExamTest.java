package org.example.personalizedstudyplanner.models;

import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExamTest {

    @Test
    void testExamConstructorAndGetters() {
        // Arrange
        int examId = 1;
        int studyPlanId = 2;
        String subject = "Mathematics";
        OffsetDateTime date = OffsetDateTime.now(ZoneOffset.UTC);
        int addressId = 3;

        // Act
        Exam exam = new Exam(examId, studyPlanId, subject, date, addressId);

        // Assert
        assertEquals(examId, exam.getExamId());
        assertEquals(studyPlanId, exam.getStudyPlanId());
        assertEquals(subject, exam.getSubject());
        assertEquals(date, exam.getDate());
        assertEquals(addressId, exam.getAddressId());
    }

    @Test
    void testExamSetters() {
        // Arrange
        Exam exam = new Exam(
                1,
                1,
                "Initial Subject",
                OffsetDateTime.now(ZoneOffset.UTC),
                1
        );

        int newExamId = 2;
        int newStudyPlanId = 3;
        String newSubject = "Physics";
        OffsetDateTime newDate = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1);
        int newAddressId = 4;

        // Act
        exam.setExamId(newExamId);
        exam.setStudyPlanId(newStudyPlanId);
        exam.setSubject(newSubject);
        exam.setDate(newDate);
        exam.setAddressId(newAddressId);

        // Assert
        assertEquals(newExamId, exam.getExamId());
        assertEquals(newStudyPlanId, exam.getStudyPlanId());
        assertEquals(newSubject, exam.getSubject());
        assertEquals(newDate, exam.getDate());
        assertEquals(newAddressId, exam.getAddressId());
    }

    @Test
    void testExamWithFutureDate() {
        // Arrange
        OffsetDateTime futureDate = OffsetDateTime.now(ZoneOffset.UTC).plusDays(7);

        // Act
        Exam exam = new Exam(1, 1, "Future Exam", futureDate, 1);

        // Assert
        assertEquals(futureDate, exam.getDate());
    }

    @Test
    void testExamWithLongSubjectName() {
        // Arrange
        String longSubject = "Advanced Mathematics and Theoretical Physics with Laboratory Practice";

        // Act
        Exam exam = new Exam(1, 1, longSubject, OffsetDateTime.now(ZoneOffset.UTC), 1);

        // Assert
        assertEquals(longSubject, exam.getSubject());
    }
}