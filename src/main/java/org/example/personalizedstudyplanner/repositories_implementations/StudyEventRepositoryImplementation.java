package org.example.personalizedstudyplanner.repositories_implementations;

import org.example.personalizedstudyplanner.models.*;
import org.example.personalizedstudyplanner.repositories.StudyEventRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StudyEventRepositoryImplementation implements StudyEventRepository {
    Logger logger = Logger.getLogger(getClass().getName());
    public static final String STATUS_EN = "status_en";
    public static final String STATUS_PL = "status_pl";
    public static final String ADDRESS_ID = "address_id";
    public static final String STATUS = "status";
    public static final String ASSIGNMENT_ID = "assignment_id";
    public static final String STUDY_PLAN_ID = "study_plan_id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DUE_DATE = "due_date";
    public static final String EXAM_ID = "exam_id";
    public static final String SUBJECT = "subject";
    public static final String DATE = "date";
    public static final String CLASS_SCHEDULE_ID = "class_schedule_id";
    private final Connection connection;

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
                        rs.getInt(ASSIGNMENT_ID),
                        rs.getInt(STUDY_PLAN_ID),
                        rs.getString(TITLE),
                        rs.getString(DESCRIPTION),
                        rs.getObject(DUE_DATE, Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        AssignmentStatus.valueOf(rs.getString(STATUS_EN)),
                        AssignmentStatus.valueOf(rs.getString(STATUS_PL))
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
                        rs.getInt(EXAM_ID),
                        rs.getInt(STUDY_PLAN_ID),
                        rs.getString(SUBJECT),
                        rs.getObject(DATE, Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getInt(ADDRESS_ID),
                        AssignmentStatus.valueOf(rs.getString(STATUS))
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
                        rs.getInt(CLASS_SCHEDULE_ID),
                        rs.getInt(STUDY_PLAN_ID),
                        rs.getString("day_of_week"),
                        rs.getString("class_name"),
                        rs.getObject("start_time", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getObject("end_time", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getInt(ADDRESS_ID),
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
                        rs.getInt(ASSIGNMENT_ID),
                        rs.getInt(STUDY_PLAN_ID),
                        rs.getString(TITLE),
                        rs.getString(DESCRIPTION),
                        rs.getObject(DUE_DATE, Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        AssignmentStatus.valueOf(rs.getString(STATUS_EN)),
                        AssignmentStatus.valueOf(rs.getString(STATUS_PL))
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
                        rs.getInt(EXAM_ID),
                        rs.getInt(STUDY_PLAN_ID),
                        rs.getString(SUBJECT),
                        rs.getObject(DATE, Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getInt(ADDRESS_ID),
                        AssignmentStatus.valueOf(rs.getString(STATUS))
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
                        rs.getInt(CLASS_SCHEDULE_ID),
                        rs.getInt(STUDY_PLAN_ID),
                        rs.getString("day_of_week"),
                        rs.getString("class_name"),
                        rs.getObject("start_time", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getObject("end_time", Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getInt(ADDRESS_ID),
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
                        rs.getInt(ASSIGNMENT_ID),
                        rs.getInt(STUDY_PLAN_ID),
                        rs.getString(TITLE),
                        rs.getString(DESCRIPTION),
                        rs.getObject(DUE_DATE, Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        AssignmentStatus.valueOf(rs.getString(STATUS_EN)),
                        AssignmentStatus.valueOf(rs.getString(STATUS_PL))
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
                        rs.getInt(EXAM_ID),
                        rs.getInt(STUDY_PLAN_ID),
                        rs.getString(SUBJECT),
                        rs.getObject(DATE, Timestamp.class).toInstant().atOffset(java.time.ZoneOffset.UTC),
                        rs.getInt(ADDRESS_ID),
                        AssignmentStatus.valueOf(rs.getString(STATUS))
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
                logger.info("Assignment status updated successfully in the database.");
            } else {
                logger.warning("Failed to update assignment status.");
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
                logger.info("Exam status updated successfully in the database.");
            } else {
                logger.warning("Failed to update exam status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
