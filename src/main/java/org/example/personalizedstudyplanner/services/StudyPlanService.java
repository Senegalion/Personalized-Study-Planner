package org.example.personalizedstudyplanner.services;

import org.example.personalizedstudyplanner.context.UserContext;
import org.example.personalizedstudyplanner.database.DatabaseUtil;
import org.example.personalizedstudyplanner.models.StudyPlan;
import org.example.personalizedstudyplanner.repositories.StudyPlanRepository;
import org.example.personalizedstudyplanner.repositories_implementations.StudyPlanRepositoryImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class StudyPlanService {
    private final StudyPlanRepository studyPlanRepository;

    public StudyPlanService() {
        Connection connection = DatabaseUtil.connect();
        if (connection != null) {
            this.studyPlanRepository = new StudyPlanRepositoryImplementation(connection);
        } else {
            throw new RuntimeException("Failed to connect to the database");
        }
    }

    public StudyPlanService(StudyPlanRepository studyPlanRepository) {
        this.studyPlanRepository = studyPlanRepository;
    }

    public List<StudyPlan> getAllStudyPlans() throws SQLException {
        int currentUserId = UserContext.getCurrentUserId();
        List<StudyPlan> studyPlans = studyPlanRepository.findAllStudyPlans();
        studyPlans.removeIf(studyPlan -> studyPlan.getStudentId() != currentUserId);
        return studyPlans;
    }

    public void createStudyPlan(String title, String description) throws SQLException {
        int currentUserId = UserContext.getCurrentUserId();
        OffsetDateTime creationDate = OffsetDateTime.now(ZoneOffset.UTC);
        StudyPlan studyPlan = new StudyPlan(0, currentUserId, title, description, creationDate);
        studyPlanRepository.save(studyPlan);
    }

    public void deleteStudyPlan(int studyPlanId) throws SQLException {
        int currentUserId = UserContext.getCurrentUserId();
        studyPlanRepository.delete(studyPlanId, currentUserId);
    }
}
