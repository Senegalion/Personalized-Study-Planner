package org.example.personalizedstudyplanner.repositories_implementations;

import org.example.personalizedstudyplanner.models.StudyPlan;
import org.example.personalizedstudyplanner.models.StudyPlanTranslation;
import org.example.personalizedstudyplanner.repositories.StudyPlanRepository;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StudyPlanRepositoryImplementation implements StudyPlanRepository {
    private Connection connection;

    public StudyPlanRepositoryImplementation(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<StudyPlan> findAllStudyPlans() throws SQLException {
        List<StudyPlan> studyPlans = new ArrayList<>();
        String query = "SELECT * FROM study_plan";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int studyPlanId = resultSet.getInt("study_plan_id");
                int studentId = resultSet.getInt("student_id");
                Timestamp timestamp = resultSet.getTimestamp("creation_date");
                OffsetDateTime creationDate = timestamp.toInstant().atOffset(ZoneOffset.UTC);

                StudyPlan studyPlan = new StudyPlan(studyPlanId, studentId, creationDate);
                studyPlan.setTranslations(fetchTranslationsForPlan(studyPlanId));
                studyPlans.add(studyPlan);
            }
        }
        return studyPlans;
    }

    private Map<String, StudyPlanTranslation> fetchTranslationsForPlan(int studyPlanId) throws SQLException {
        Map<String, StudyPlanTranslation> translations = new ConcurrentHashMap<>();
        String query = "SELECT language_code, title, description FROM study_plan_translation WHERE study_plan_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studyPlanId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String langCode = rs.getString("language_code");
                String title = rs.getString("title");
                String description = rs.getString("description");

                StudyPlanTranslation translation = new StudyPlanTranslation(studyPlanId, langCode, title, description);
                translations.put(langCode, translation);
            }
        }
        return translations;
    }

    @Override
    public void save(StudyPlan studyPlan) throws SQLException {
        String insertPlanQuery = "INSERT INTO study_plan (student_id, creation_date) VALUES (?, ?) RETURNING study_plan_id";

        try (PreparedStatement insertPlanStmt = connection.prepareStatement(insertPlanQuery)) {
            insertPlanStmt.setInt(1, studyPlan.getStudentId());
            insertPlanStmt.setObject(2, studyPlan.getCreationDate());
            ResultSet rs = insertPlanStmt.executeQuery();
            if (rs.next()) {
                int studyPlanId = rs.getInt(1);
                studyPlan.setStudyPlanId(studyPlanId);

                String insertTranslationQuery = "INSERT INTO study_plan_translation (study_plan_id, language_code, title, description) VALUES (?, ?, ?, ?)";
                try (PreparedStatement insertTranslationStmt = connection.prepareStatement(insertTranslationQuery)) {
                    insertTranslationStmt.setInt(1, studyPlanId);
                    for (StudyPlanTranslation translation : studyPlan.getTranslations().values()) {
                        insertTranslationStmt.setString(2, translation.getLanguageCode());
                        insertTranslationStmt.setString(3, translation.getTitle());
                        insertTranslationStmt.setString(4, translation.getDescription());
                        insertTranslationStmt.addBatch();
                    }
                    insertTranslationStmt.executeBatch();
                }
            }
        }
    }

    @Override
    public void delete(int studyPlanId, int currentUserId) throws SQLException {
        String deleteAssignmentsQuery = "DELETE FROM assignment WHERE study_plan_id = ?";
        String deleteExamsQuery = "DELETE FROM exam WHERE study_plan_id = ?";
        String deleteSchedulesQuery = "DELETE FROM class_schedule WHERE study_plan_id = ?";
        String deleteStudyPlanQuery = "DELETE FROM study_plan WHERE study_plan_id = ? AND student_id = ?";

        try (PreparedStatement deleteAssignmentsStmt = connection.prepareStatement(deleteAssignmentsQuery);
             PreparedStatement deleteExamsStmt = connection.prepareStatement(deleteExamsQuery);
             PreparedStatement deleteSchedulesStmt = connection.prepareStatement(deleteSchedulesQuery);
             PreparedStatement deleteStudyPlanStmt = connection.prepareStatement(deleteStudyPlanQuery)) {

            deleteAssignmentsStmt.setInt(1, studyPlanId);
            deleteAssignmentsStmt.executeUpdate();

            deleteExamsStmt.setInt(1, studyPlanId);
            deleteExamsStmt.executeUpdate();

            deleteSchedulesStmt.setInt(1, studyPlanId);
            deleteSchedulesStmt.executeUpdate();

            deleteStudyPlanStmt.setInt(1, studyPlanId);
            deleteStudyPlanStmt.setInt(2, currentUserId);
            deleteStudyPlanStmt.executeUpdate();
        }
    }
}
