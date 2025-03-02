package org.example.personalizedstudyplanner.services;

import org.example.personalizedstudyplanner.context.StudyPlanContext;
import org.example.personalizedstudyplanner.database.DatabaseUtil;
import org.example.personalizedstudyplanner.models.Address;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.repositories.StudyEventRepository;
import org.example.personalizedstudyplanner.repositories_implementations.StudyEventRepositoryImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class StudyEventService {
    private final StudyEventRepository studyEventRepository;

    public StudyEventService() {
        Connection connection = DatabaseUtil.connect();
        if (connection != null) {
            this.studyEventRepository = new StudyEventRepositoryImplementation(connection);
        } else {
            throw new RuntimeException("Failed to connect to the database");
        }
    }

    public List<Assignment> getAssignmentsForDate(LocalDate date) {
        return studyEventRepository.getAssignmentsForDate(date, StudyPlanContext.getCurrentStudyPlanId());
    }

    public List<Exam> getExamsForDate(LocalDate date) {
        return studyEventRepository.getExamsForDate(date, StudyPlanContext.getCurrentStudyPlanId());
    }

    public List<ClassSchedule> getClassesForDate(LocalDate date) {
        return studyEventRepository.getClassesForDate(date, StudyPlanContext.getCurrentStudyPlanId());
    }

    public void addAssignment(Assignment assignment) {
        studyEventRepository.addAssignment(assignment);
    }

    public void addExam(Exam exam, Address address) {
        studyEventRepository.addExam(exam, address);
    }

    public void addClassSchedule(ClassSchedule classSchedule, Address address) {
        studyEventRepository.addClassSchedule(classSchedule, address);
    }

    public List<Assignment> getUpcomingAssignments(int daysAhead, int studyPlanId) throws SQLException {
        return studyEventRepository.getUpcomingAssignments(daysAhead, studyPlanId);
    }

    public List<Exam> getUpcomingExams(int daysAhead, int studyPlanId) throws SQLException {
        return studyEventRepository.getUpcomingExams(daysAhead, studyPlanId);
    }

    public List<ClassSchedule> getUpcomingClasses(int daysAhead, int studyPlanId) throws SQLException {
        return studyEventRepository.getUpcomingClasses(daysAhead, studyPlanId);
    }

    public List<Assignment> getAllAssignments(int studyPlanId) {
        return studyEventRepository.getAllAssignments(studyPlanId);
    }

    public List<Exam> getAllExams(int studyPlanId) {
        return studyEventRepository.getAllExams(studyPlanId);
    }

    public void updateAssignmentStatus(Assignment assignment) {
        studyEventRepository.updateAssignmentStatus(assignment);
    }

    public void updateExamStatus(Exam exam) {
        studyEventRepository.updateExamStatus(exam);
    }
}
