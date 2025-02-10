package org.example.personalizedstudyplanner.repositories_implementations;

import org.example.personalizedstudyplanner.models.*;
import org.example.personalizedstudyplanner.repositories.StudyEventRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudyEventRepositoryImplementation implements StudyEventRepository {

    private Connection connection;

    public StudyEventRepositoryImplementation(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Assignment> getAssignmentsForDate(LocalDate date) {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignment WHERE DATE(due_date) = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                assignments.add(new Assignment(
                        rs.getInt("assignment_id"),
                        rs.getInt("study_plan_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getObject("due_date", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        AssignmentStatus.valueOf(rs.getString("status"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignments;
    }

    @Override
    public List<Exam> getExamsForDate(LocalDate date) {
        List<Exam> exams = new ArrayList<>();
        String sql = "SELECT * FROM exam WHERE DATE(date) = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                exams.add(new Exam(
                        rs.getInt("exam_id"),
                        rs.getInt("study_plan_id"),
                        rs.getString("subject"),
                        rs.getObject("date", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getInt("address_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exams;
    }

    @Override
    public List<ClassSchedule> getClassesForDate(LocalDate date) {
        List<ClassSchedule> classSchedules = new ArrayList<>();
        String sql = "SELECT * FROM class_schedule WHERE DATE(start_time) = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                classSchedules.add(new ClassSchedule(
                        rs.getInt("class_schedule_id"),
                        rs.getInt("study_plan_id"),
                        rs.getString("day_of_week"),
                        rs.getString("class_name"),
                        rs.getObject("start_time", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getObject("end_time", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getInt("address_id"),
                        rs.getString("recurrence_pattern")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classSchedules;
    }

    @Override
    public void addAssignment(Assignment assignment) {
        String sql = "INSERT INTO assignment (study_plan_id, title, description, due_date, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, assignment.getStudyPlanId());
            stmt.setString(2, assignment.getTitle());
            stmt.setString(3, assignment.getDescription());
            stmt.setTimestamp(4, Timestamp.from(assignment.getDueDate().toInstant()));
            stmt.setString(5, assignment.getStatus().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addExam(Exam exam, Address address) {
        AddressRepositoryImplementation addressRepository = new AddressRepositoryImplementation(connection);
        int addressId = addressRepository.getOrCreateAddress(address);

        String sql = "INSERT INTO exam (study_plan_id, subject, date, address_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, exam.getStudyPlanId());
            stmt.setString(2, exam.getSubject());
            stmt.setTimestamp(3, Timestamp.from(exam.getDate().toInstant()));
            stmt.setInt(4, addressId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addClassSchedule(ClassSchedule classSchedule, Address address) {
        AddressRepositoryImplementation addressRepository = new AddressRepositoryImplementation(connection);
        int addressId = addressRepository.getOrCreateAddress(address);

        String sql = "INSERT INTO class_schedule (study_plan_id, day_of_week, class_name, start_time, end_time, address_id, recurrence_pattern) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, classSchedule.getStudyPlanId());
            stmt.setString(2, classSchedule.getDayOfWeek());
            stmt.setString(3, classSchedule.getClassName());
            stmt.setTimestamp(4, Timestamp.from(classSchedule.getStartTime().toInstant()));
            stmt.setTimestamp(5, Timestamp.from(classSchedule.getEndTime().toInstant()));
            stmt.setInt(6, addressId);
            stmt.setString(7, classSchedule.getRecurrencePattern());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
