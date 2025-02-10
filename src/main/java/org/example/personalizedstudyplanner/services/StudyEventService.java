package org.example.personalizedstudyplanner.services;

import org.example.personalizedstudyplanner.database.DatabaseUtil;
import org.example.personalizedstudyplanner.models.Address;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.example.personalizedstudyplanner.models.Exam;
import org.example.personalizedstudyplanner.repositories.StudyEventRepository;
import org.example.personalizedstudyplanner.repositories_implementations.StudyEventRepositoryImplementation;

import java.sql.Connection;
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
        return studyEventRepository.getAssignmentsForDate(date);
    }

    public List<Exam> getExamsForDate(LocalDate date) {
        return studyEventRepository.getExamsForDate(date);
    }

    public List<ClassSchedule> getClassesForDate(LocalDate date) {
        return studyEventRepository.getClassesForDate(date);
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
}
