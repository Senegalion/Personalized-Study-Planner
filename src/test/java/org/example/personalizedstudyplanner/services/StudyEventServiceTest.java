//package org.example.personalizedstudyplanner.services;
//
//import org.example.personalizedstudyplanner.context.StudyPlanContext;
//import org.example.personalizedstudyplanner.config.database.DatabaseUtil;
//import org.example.personalizedstudyplanner.models.*;
//import org.example.personalizedstudyplanner.repositories.StudyEventRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.time.OffsetDateTime;
//import java.time.ZoneOffset;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mockStatic;
//
//class StudyEventServiceTest {
//
//    private StudyEventService studyEventService;
//    private TestStudyEventRepository testRepository;
//
//    @BeforeEach
//    void setUp() {
//        testRepository = new TestStudyEventRepository();
//        try {
//            java.lang.reflect.Field field = StudyEventService.class.getDeclaredField("studyEventRepository");
//            field.setAccessible(true);
//            studyEventService = new StudyEventService() {
//                {
//                    try {
//                        field.set(this, testRepository);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            };
//            StudyPlanContext.setCurrentStudyPlanId(1);
//        } catch (Exception e) {
//            fail("Failed to set up test: " + e.getMessage());
//        }
//    }
//
//    @Test
//    void getAssignmentsForDate_ShouldReturnListOfAssignments() {
//        LocalDate testDate = LocalDate.now();
//        Assignment testAssignment = new Assignment(1, 1, "Test Assignment", "Description",
//                OffsetDateTime.now(ZoneOffset.UTC), AssignmentStatus.PENDING);
//        testRepository.addTestAssignment(testAssignment);
//
//        List<Assignment> assignments = studyEventService.getAssignmentsForDate(testDate);
//
//        assertEquals(1, assignments.size());
//        assertEquals(testAssignment.getTitle(), assignments.get(0).getTitle());
//    }
//
//    @Test
//    void getExamsForDate_ShouldReturnListOfExams() {
//        LocalDate testDate = LocalDate.now();
//        Exam testExam = new Exam(1, 1, "Test Exam", OffsetDateTime.now(ZoneOffset.UTC), 1, AssignmentStatus.PENDING);
//        testRepository.addTestExam(testExam);
//
//        List<Exam> exams = studyEventService.getExamsForDate(testDate);
//
//        assertEquals(1, exams.size());
//        assertEquals(testExam.getSubject(), exams.get(0).getSubject());
//    }
//
//    @Test
//    void getClassesForDate_ShouldReturnListOfClasses() {
//        LocalDate testDate = LocalDate.now();
//        ClassSchedule testClass = new ClassSchedule(1, 1, "Monday", "Test Class",
//                OffsetDateTime.now(ZoneOffset.UTC), OffsetDateTime.now(ZoneOffset.UTC).plusHours(1),
//                1, "WEEKLY");
//        testRepository.addTestClass(testClass);
//
//        List<ClassSchedule> classes = studyEventService.getClassesForDate(testDate);
//
//        assertEquals(1, classes.size());
//        assertEquals(testClass.getClassName(), classes.get(0).getClassName());
//    }
//
//    @Test
//    void addAssignment_ShouldAddToRepository() {
//        Assignment assignment = new Assignment(1, 1, "Test Assignment", "Description",
//                OffsetDateTime.now(ZoneOffset.UTC), AssignmentStatus.PENDING);
//
//        studyEventService.addAssignment(assignment);
//
//        assertTrue(testRepository.containsAssignment(assignment));
//    }
//
//    @Test
//    void addExam_ShouldAddToRepository() {
//        Exam exam = new Exam(1, 1, "Test Exam", OffsetDateTime.now(ZoneOffset.UTC), 1, AssignmentStatus.PENDING);
//        Address address = new Address(1, "Country", "City", "Street", 123, "12345");
//
//        studyEventService.addExam(exam, address);
//
//        assertTrue(testRepository.containsExam(exam));
//    }
//
//    @Test
//    void addClassSchedule_ShouldAddToRepository() {
//        ClassSchedule classSchedule = new ClassSchedule(1, 1, "Monday", "Test Class",
//                OffsetDateTime.now(ZoneOffset.UTC), OffsetDateTime.now(ZoneOffset.UTC).plusHours(1),
//                1, "WEEKLY");
//        Address address = new Address(1, "Country", "City", "Street", 123, "12345");
//
//        studyEventService.addClassSchedule(classSchedule, address);
//
//        assertTrue(testRepository.containsClass(classSchedule));
//    }
//
//    @Test
//    void getUpcomingAssignments_ShouldReturnListOfUpcomingAssignments() throws SQLException {
//        Assignment testAssignment = new Assignment(1, 1, "Test Assignment", "Description",
//                OffsetDateTime.now(ZoneOffset.UTC).plusDays(1), AssignmentStatus.PENDING);
//        testRepository.addTestAssignment(testAssignment);
//
//        List<Assignment> assignments = studyEventService.getUpcomingAssignments(7, 1);
//
//        assertEquals(1, assignments.size());
//        assertEquals(testAssignment.getTitle(), assignments.get(0).getTitle());
//    }
//
//    @Test
//    void getUpcomingExams_ShouldReturnListOfUpcomingExams() throws SQLException {
//        Exam testExam = new Exam(1, 1, "Test Exam", OffsetDateTime.now(ZoneOffset.UTC).plusDays(1), 1, AssignmentStatus.PENDING);
//        testRepository.addTestExam(testExam);
//
//        List<Exam> exams = studyEventService.getUpcomingExams(7, 1);
//
//        assertEquals(1, exams.size());
//        assertEquals(testExam.getSubject(), exams.get(0).getSubject());
//    }
//
//    @Test
//    void getUpcomingClasses_ShouldReturnListOfUpcomingClasses() throws SQLException {
//        ClassSchedule testClass = new ClassSchedule(1, 1, "Monday", "Test Class",
//                OffsetDateTime.now(ZoneOffset.UTC).plusDays(1), OffsetDateTime.now(ZoneOffset.UTC).plusDays(1).plusHours(1),
//                1, "WEEKLY");
//        testRepository.addTestClass(testClass);
//
//        List<ClassSchedule> classes = studyEventService.getUpcomingClasses(7, 1);
//
//        assertEquals(1, classes.size());
//        assertEquals(testClass.getClassName(), classes.get(0).getClassName());
//    }
//
//    @Test
//    void getAllAssignments_ShouldReturnListOfAllAssignments() {
//        Assignment testAssignment = new Assignment(1, 1, "Test Assignment", "Description",
//                OffsetDateTime.now(ZoneOffset.UTC), AssignmentStatus.PENDING);
//        testRepository.addTestAssignment(testAssignment);
//
//        List<Assignment> assignments = studyEventService.getAllAssignments(1);
//
//        assertEquals(1, assignments.size());
//        assertEquals(testAssignment.getTitle(), assignments.get(0).getTitle());
//    }
//
//    @Test
//    void getAllExams_ShouldReturnListOfAllExams() {
//        Exam testExam = new Exam(1, 1, "Test Exam", OffsetDateTime.now(ZoneOffset.UTC), 1, AssignmentStatus.PENDING);
//        testRepository.addTestExam(testExam);
//
//        List<Exam> exams = studyEventService.getAllExams(1);
//
//        assertEquals(1, exams.size());
//        assertEquals(testExam.getSubject(), exams.get(0).getSubject());
//    }
//
//    @Test
//    void updateAssignmentStatus_ShouldUpdateStatusInRepository() {
//        Assignment assignment = new Assignment(1, 1, "Test Assignment", "Description",
//                OffsetDateTime.now(ZoneOffset.UTC), AssignmentStatus.PENDING);
//        testRepository.addTestAssignment(assignment);
//
//        assignment.setStatus(AssignmentStatus.COMPLETED);
//        studyEventService.updateAssignmentStatus(assignment);
//
//        assertEquals(AssignmentStatus.COMPLETED, testRepository.getAssignmentsForDate(LocalDate.now(), 1).get(0).getStatus());
//    }
//
//    @Test
//    void updateExamStatus_ShouldUpdateStatusInRepository() {
//        Exam exam = new Exam(1, 1, "Test Exam", OffsetDateTime.now(ZoneOffset.UTC), 1, AssignmentStatus.PENDING);
//        testRepository.addTestExam(exam);
//
//        exam.setStatus(AssignmentStatus.COMPLETED);
//        studyEventService.updateExamStatus(exam);
//
//        assertEquals(AssignmentStatus.COMPLETED, testRepository.getExamsForDate(LocalDate.now(), 1).get(0).getStatus());
//    }
//
//    @Test
//    void constructor_shouldThrowRuntimeException_whenDatabaseConnectionFails() {
//        try (MockedStatic<DatabaseUtil> mockedDatabaseUtil = mockStatic(DatabaseUtil.class)) {
//            mockedDatabaseUtil.when(DatabaseUtil::connect).thenReturn(null);
//            RuntimeException exception = assertThrows(RuntimeException.class, StudyEventService::new);
//            assertEquals("Failed to connect to the database", exception.getMessage());
//        }
//    }
//
//    private static class TestStudyEventRepository implements StudyEventRepository {
//        private final List<Assignment> assignments = new ArrayList<>();
//        private final List<Exam> exams = new ArrayList<>();
//        private final List<ClassSchedule> classes = new ArrayList<>();
//
//        @Override
//        public List<Assignment> getAssignmentsForDate(LocalDate date, int studyPlanId) {
//            return new ArrayList<>(assignments);
//        }
//
//        @Override
//        public List<Exam> getExamsForDate(LocalDate date, int studyPlanId) {
//            return new ArrayList<>(exams);
//        }
//
//        @Override
//        public List<ClassSchedule> getClassesForDate(LocalDate date, int studyPlanId) {
//            return new ArrayList<>(classes);
//        }
//
//        @Override
//        public void addAssignment(Assignment assignment) {
//            assignments.add(assignment);
//        }
//
//        @Override
//        public void addExam(Exam exam, Address address) {
//            exams.add(exam);
//        }
//
//        @Override
//        public void addClassSchedule(ClassSchedule classSchedule, Address address) {
//            classes.add(classSchedule);
//        }
//
//        @Override
//        public List<Assignment> getUpcomingAssignments(int daysAhead, int studyPlanId) throws SQLException {
//            return new ArrayList<>(assignments);
//        }
//
//        @Override
//        public List<Exam> getUpcomingExams(int daysAhead, int studyPlanId) throws SQLException {
//            return new ArrayList<>(exams);
//        }
//
//        @Override
//        public List<ClassSchedule> getUpcomingClasses(int daysAhead, int studyPlanId) throws SQLException {
//            return new ArrayList<>(classes);
//        }
//
//        @Override
//        public List<Assignment> getAllAssignments(int studyPlanId) {
//            return new ArrayList<>(assignments);
//        }
//
//        @Override
//        public List<Exam> getAllExams(int studyPlanId) {
//            return new ArrayList<>(exams);
//        }
//
//        @Override
//        public void updateAssignmentStatus(Assignment assignment) {
//            assignments.replaceAll(a -> a.getAssignmentId() == assignment.getAssignmentId() ? assignment : a);
//        }
//
//        @Override
//        public void updateExamStatus(Exam exam) {
//            exams.replaceAll(e -> e.getExamId() == exam.getExamId() ? exam : e);
//        }
//
//        public void addTestAssignment(Assignment assignment) {
//            assignments.add(assignment);
//        }
//
//        public void addTestExam(Exam exam) {
//            exams.add(exam);
//        }
//
//        public void addTestClass(ClassSchedule classSchedule) {
//            classes.add(classSchedule);
//        }
//
//        public boolean containsAssignment(Assignment assignment) {
//            return assignments.contains(assignment);
//        }
//
//        public boolean containsExam(Exam exam) {
//            return exams.contains(exam);
//        }
//
//        public boolean containsClass(ClassSchedule classSchedule) {
//            return classes.contains(classSchedule);
//        }
//
//
//    }
//}