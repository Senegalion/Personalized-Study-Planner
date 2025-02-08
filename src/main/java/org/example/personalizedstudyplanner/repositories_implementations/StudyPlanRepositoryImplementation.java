package org.example.personalizedstudyplanner.repositories_implementations;

import org.example.personalizedstudyplanner.models.StudyPlan;
import org.example.personalizedstudyplanner.repositories.StudyPlanRepository;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

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
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Timestamp timestamp = resultSet.getTimestamp("creation_date");
                OffsetDateTime creationDate = timestamp.toInstant().atOffset(ZoneOffset.UTC);

                studyPlans.add(new StudyPlan(studyPlanId, studentId, title, description, creationDate));
            }
        }
        return studyPlans;
    }

    @Override
    public void save(StudyPlan studyPlan) throws SQLException {
        String query = "INSERT INTO study_plan (student_id, title, description, creation_date) VALUES (?, ?, ?, ?)";
        System.out.println(studyPlan.getStudentId());
        System.out.println(studyPlan.getTitle());
        System.out.println(studyPlan.getDescription());
        System.out.println(studyPlan.getCreationDate());

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studyPlan.getStudentId());
            preparedStatement.setString(2, studyPlan.getTitle());
            preparedStatement.setString(3, studyPlan.getDescription());
            studyPlan.setCreationDate(OffsetDateTime.now(ZoneOffset.UTC));
            System.out.println(studyPlan.getCreationDate());
            preparedStatement.setObject(4, studyPlan.getCreationDate());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(int studyPlanId, int currentUserId) throws SQLException {
        String query = "DELETE FROM study_plan WHERE study_plan_id = ? AND student_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studyPlanId);
            preparedStatement.setInt(2, currentUserId);
            preparedStatement.executeUpdate();
        }
    }
}
