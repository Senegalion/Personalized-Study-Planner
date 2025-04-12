package org.example.personalizedstudyplanner.services;

import org.example.personalizedstudyplanner.config.database.DatabaseUtil;
import org.example.personalizedstudyplanner.context.UserContext;
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

    public List<StudyPlan> getAllStudyPlans() throws SQLException {
        int currentUserId = UserContext.getCurrentUserId();
        List<StudyPlan> studyPlans = studyPlanRepository.findAllStudyPlans();
        studyPlans.removeIf(studyPlan -> studyPlan.getStudentId() != currentUserId);
        return studyPlans;
    }

    public void createStudyPlanWithTranslations(String titleEN, String descEN, String titlePL, String descPL, String titleZH, String descZH) throws SQLException {
        int currentUserId = UserContext.getCurrentUserId();
        OffsetDateTime creationDate = OffsetDateTime.now(ZoneOffset.UTC);

        StudyPlan studyPlan = new StudyPlan(0, currentUserId, creationDate);
        studyPlan.addTranslation("en", titleEN, descEN);
        studyPlan.addTranslation("pl", titlePL, descPL);

        if (!titleZH.isEmpty() && !descZH.isEmpty()) {
            studyPlan.addTranslation("zh", titleZH, descZH);
        }

        studyPlanRepository.save(studyPlan);
    }

    public void deleteStudyPlan(int studyPlanId) throws SQLException {
        int currentUserId = UserContext.getCurrentUserId();
        studyPlanRepository.delete(studyPlanId, currentUserId);
    }
}
