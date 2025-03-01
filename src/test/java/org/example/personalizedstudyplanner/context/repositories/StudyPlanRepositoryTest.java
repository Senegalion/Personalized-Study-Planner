package org.example.personalizedstudyplanner.context.repositories;

import org.example.personalizedstudyplanner.models.StudyPlan;
import org.example.personalizedstudyplanner.repositories.StudyPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudyPlanRepositoryTest {

    private TestStudyPlanRepository repository;
    private StudyPlan testStudyPlan;

    @BeforeEach
    void setUp() {
        repository = new TestStudyPlanRepository();

        testStudyPlan = new StudyPlan(
                1,
                1,
                "Test Study Plan",
                "Test Description",
                OffsetDateTime.now(ZoneOffset.UTC)
        );
    }

    @Test
    void findAllStudyPlans_ShouldReturnAllStudyPlans() throws SQLException {
        repository.addTestStudyPlan(testStudyPlan);

        List<StudyPlan> studyPlans = repository.findAllStudyPlans();

        assertNotNull(studyPlans);
        assertEquals(1, studyPlans.size());
        assertEquals(testStudyPlan.getTitle(), studyPlans.get(0).getTitle());
        assertEquals(testStudyPlan.getDescription(), studyPlans.get(0).getDescription());
    }

    @Test
    void findAllStudyPlans_WhenEmpty_ShouldReturnEmptyList() throws SQLException {
        List<StudyPlan> studyPlans = repository.findAllStudyPlans();

        assertNotNull(studyPlans);
        assertTrue(studyPlans.isEmpty());
    }

    @Test
    void save_ShouldAddStudyPlanSuccessfully() throws SQLException {
        repository.save(testStudyPlan);

        List<StudyPlan> savedPlans = repository.findAllStudyPlans();
        assertEquals(1, savedPlans.size());
        assertEquals(testStudyPlan.getTitle(), savedPlans.get(0).getTitle());
    }

    @Test
    void delete_WithValidIds_ShouldRemoveStudyPlan() throws SQLException {
        repository.save(testStudyPlan);

        repository.delete(testStudyPlan.getStudyPlanId(), testStudyPlan.getStudentId());

        List<StudyPlan> remainingPlans = repository.findAllStudyPlans();
        assertTrue(remainingPlans.isEmpty());
    }

    @Test
    void delete_WithInvalidStudyPlanId_ShouldNotModifyRepository() throws SQLException {

        repository.save(testStudyPlan);

        repository.delete(-1, testStudyPlan.getStudentId());

        List<StudyPlan> remainingPlans = repository.findAllStudyPlans();
        assertEquals(1, remainingPlans.size());
    }

    @Test
    void delete_WithInvalidUserId_ShouldNotModifyRepository() throws SQLException {
        repository.save(testStudyPlan);

        repository.delete(testStudyPlan.getStudyPlanId(), -1);

        List<StudyPlan> remainingPlans = repository.findAllStudyPlans();
        assertEquals(1, remainingPlans.size());
    }

    private static class TestStudyPlanRepository implements StudyPlanRepository {
        private final List<StudyPlan> studyPlans = new java.util.ArrayList<>();

        void addTestStudyPlan(StudyPlan studyPlan) {
            studyPlans.add(studyPlan);
        }

        @Override
        public List<StudyPlan> findAllStudyPlans() {
            return new java.util.ArrayList<>(studyPlans);
        }

        @Override
        public void save(StudyPlan studyPlan) {
            studyPlans.add(studyPlan);
        }

        @Override
        public void delete(int studyPlanId, int currentUserId) {
            studyPlans.removeIf(plan ->
                    plan.getStudyPlanId() == studyPlanId &&
                            plan.getStudentId() == currentUserId
            );
        }
    }
}
