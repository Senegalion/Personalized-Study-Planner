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
    public List<Assignment> getAssignmentsForDate(LocalDate date, int studyPlanId) {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignment WHERE DATE(due_date) = ? AND study_plan_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            stmt.setInt(2, studyPlanId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                assignments.add(new Assignment(
                        rs.getInt("assignment_id"),
                        rs.getInt("study_plan_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getObject("due_date", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        AssignmentStatus.valueOf(rs.getString("status_en")),
                        AssignmentStatus.valueOf(rs.getString("status_pl"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignments;
    }

    @Override
    public List<Exam> getExamsForDate(LocalDate date, int studyPlanId) {
        List<Exam> exams = new ArrayList<>();
        String sql = "SELECT * FROM exam WHERE DATE(date) = ? AND study_plan_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            stmt.setInt(2, studyPlanId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                exams.add(new Exam(
                        rs.getInt("exam_id"),
                        rs.getInt("study_plan_id"),
                        rs.getString("subject"),
                        rs.getObject("date", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getInt("address_id"),
                        AssignmentStatus.valueOf(rs.getString("status"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exams;
    }

    @Override
    public List<ClassSchedule> getClassesForDate(LocalDate date, int studyPlanId) {
        List<ClassSchedule> classSchedules = new ArrayList<>();
        String sql = "SELECT * FROM class_schedule WHERE DATE(start_time) = ? AND study_plan_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            stmt.setInt(2, studyPlanId);
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
        String sql = "INSERT INTO assignment (study_plan_id, title, description, due_date, status_en, status_pl) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, assignment.getStudyPlanId());
            stmt.setString(2, assignment.getTitle());
            stmt.setString(3, assignment.getDescription());
            stmt.setTimestamp(4, Timestamp.from(assignment.getDueDate().toInstant()));
            stmt.setString(5, assignment.getStatusEn().name());
            stmt.setString(6, assignment.getStatusPl().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addExam(Exam exam, Address address) {
        AddressRepositoryImplementation addressRepository = new AddressRepositoryImplementation(connection);
        int addressId = addressRepository.getOrCreateAddress(address);

        String sql = "INSERT INTO exam (study_plan_id, subject, date, address_id, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, exam.getStudyPlanId());
            stmt.setString(2, exam.getSubject());
            stmt.setTimestamp(3, Timestamp.from(exam.getDate().toInstant()));
            stmt.setInt(4, addressId);
            stmt.setString(5, exam.getStatus().name());
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

    @Override
    public List<Assignment> getUpcomingAssignments(int daysAhead, int studyPlanId) throws SQLException {
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusDays(daysAhead);

        String query = "SELECT * FROM assignment WHERE due_date BETWEEN ? AND ? AND study_plan_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, today);
            stmt.setObject(2, maxDate);
            stmt.setInt(3, studyPlanId);

            ResultSet rs = stmt.executeQuery();
            List<Assignment> assignments = new ArrayList<>();

            while (rs.next()) {
                assignments.add(new Assignment(
                        rs.getInt("assignment_id"),
                        rs.getInt("study_plan_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getObject("due_date", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        AssignmentStatus.valueOf(rs.getString("status_en")),
                        AssignmentStatus.valueOf(rs.getString("status_pl"))
                ));
            }
            return assignments;
        }
    }

    @Override
    public List<Exam> getUpcomingExams(int daysAhead, int studyPlanId) throws SQLException {
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusDays(daysAhead);

        String query = "SELECT * FROM exam WHERE date BETWEEN ? AND ? AND study_plan_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, today);
            stmt.setObject(2, maxDate);
            stmt.setInt(3, studyPlanId);

            ResultSet rs = stmt.executeQuery();
            List<Exam> exams = new ArrayList<>();

            while (rs.next()) {
                exams.add(new Exam(
                        rs.getInt("exam_id"),
                        rs.getInt("study_plan_id"),
                        rs.getString("subject"),
                        rs.getObject("date", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getInt("address_id"),
                        AssignmentStatus.valueOf(rs.getString("status"))
                ));
            }
            return exams;
        }
    }

    @Override
    public List<ClassSchedule> getUpcomingClasses(int daysAhead, int studyPlanId) throws SQLException {
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusDays(daysAhead);

        String query = "SELECT * FROM class_schedule WHERE start_time BETWEEN ? AND ? AND study_plan_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, today);
            stmt.setObject(2, maxDate);
            stmt.setInt(3, studyPlanId);

            ResultSet rs = stmt.executeQuery();
            List<ClassSchedule> classSchedules = new ArrayList<>();

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
            return classSchedules;
        }
    }

    @Override
    public List<Assignment> getAllAssignments(int studyPlanId) {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignment AND study_plan_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, studyPlanId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                assignments.add(new Assignment(
                        rs.getInt("assignment_id"),
                        rs.getInt("study_plan_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getObject("due_date", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        AssignmentStatus.valueOf(rs.getString("status_en")),
                        AssignmentStatus.valueOf(rs.getString("status_pl"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignments;
    }

    @Override
    public List<Exam> getAllExams(int studyPlanId) {
        List<Exam> exams = new ArrayList<>();
        String sql = "SELECT * FROM exam AND study_plan_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, studyPlanId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                exams.add(new Exam(
                        rs.getInt("exam_id"),
                        rs.getInt("study_plan_id"),
                        rs.getString("subject"),
                        rs.getObject("date", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getInt("address_id"),
                        AssignmentStatus.valueOf(rs.getString("status"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exams;
    }

    @Override
    public void updateAssignmentStatus(Assignment assignment) {
        String updateQuery = "UPDATE assignment SET status_en = ?, status_pl = ? WHERE assignment_id = ? AND study_plan_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, assignment.getStatusEn().name());
            stmt.setString(2, assignment.getStatusPl().name());
            stmt.setInt(3, assignment.getAssignmentId());
            stmt.setInt(4, assignment.getStudyPlanId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Assignment status updated successfully in the database.");
            } else {
                System.out.println("Failed to update assignment status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateExamStatus(Exam exam) {
        String updateQuery = "UPDATE exam SET status = ? WHERE exam_id = ? AND study_plan_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, exam.getStatus().name());
            stmt.setInt(2, exam.getExamId());
            stmt.setInt(3, exam.getStudyPlanId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Exam status updated successfully in the database.");
            } else {
                System.out.println("Failed to update exam status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
