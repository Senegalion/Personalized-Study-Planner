package org.example.personalizedstudyplanner.repositories;

import org.example.personalizedstudyplanner.models.Address;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.example.personalizedstudyplanner.models.Exam;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface StudyEventRepository {
    List<Assignment> getAssignmentsForDate(LocalDate date, int studyPlanId);

    List<Exam> getExamsForDate(LocalDate date, int studyPlanId);

    List<ClassSchedule> getClassesForDate(LocalDate date, int studyPlanId);

    void addAssignment(Assignment assignment);

    void addExam(Exam exam, Address address);

    void addClassSchedule(ClassSchedule classSchedule, Address address);

    List<Assignment> getUpcomingAssignments(int daysAhead, int studyPlanId) throws SQLException;

    List<Exam> getUpcomingExams(int daysAhead, int studyPlanId) throws SQLException;

    List<ClassSchedule> getUpcomingClasses(int daysAhead, int studyPlanId) throws SQLException;

    List<Assignment> getAllAssignments(int studyPlanId);

    List<Exam> getAllExams(int studyPlanId);

    void updateAssignmentStatus(Assignment assignment);

    void updateExamStatus(Exam exam);
}
