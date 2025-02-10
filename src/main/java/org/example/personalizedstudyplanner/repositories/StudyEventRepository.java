package org.example.personalizedstudyplanner.repositories;

import org.example.personalizedstudyplanner.models.Address;
import org.example.personalizedstudyplanner.models.Assignment;
import org.example.personalizedstudyplanner.models.ClassSchedule;
import org.example.personalizedstudyplanner.models.Exam;

import java.time.LocalDate;
import java.util.List;

public interface StudyEventRepository {
    List<Assignment> getAssignmentsForDate(LocalDate date);

    List<Exam> getExamsForDate(LocalDate date);

    List<ClassSchedule> getClassesForDate(LocalDate date);

    void addAssignment(Assignment assignment);

    void addExam(Exam exam, Address address);

    void addClassSchedule(ClassSchedule classSchedule, Address address);
}
