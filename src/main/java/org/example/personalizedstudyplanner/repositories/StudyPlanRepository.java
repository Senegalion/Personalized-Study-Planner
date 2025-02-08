package org.example.personalizedstudyplanner.repositories;

import org.example.personalizedstudyplanner.models.StudyPlan;

import java.sql.SQLException;
import java.util.List;

public interface StudyPlanRepository {
    List<StudyPlan> findAllStudyPlans() throws SQLException;

    void save(StudyPlan studyPlan) throws SQLException;

    void delete(int studyPlanId, int currentUserId) throws SQLException;
}
