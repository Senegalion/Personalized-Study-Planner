package org.example.personalizedstudyplanner.repositories;

import org.example.personalizedstudyplanner.models.StudyPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudyPlanRepositoryTest {

    private TestStudyPlanRepository repository;
    private StudyPlan testStudyPlan;

    @BeforeEach
    void setUp() {
        repository = new TestStudyPlanRepository();

        // Initialize test study plan
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
        // Arrange
        repository.addTestStudyPlan(testStudyPlan);

        // Act
        List<StudyPlan> studyPlans = repository.findAllStudyPlans();

        // Assert
        assertNotNull(studyPlans);
        assertEquals(1, studyPlans.size());
        assertEquals(testStudyPlan.getTitle(), studyPlans.get(0).getTitle());
        assertEquals(testStudyPlan.getDescription(), studyPlans.get(0).getDescription());
    }

    @Test
    void findAllStudyPlans_WhenEmpty_ShouldReturnEmptyList() throws SQLException {
        // Act
        List<StudyPlan> studyPlans = repository.findAllStudyPlans();

        // Assert
        assertNotNull(studyPlans);
        assertTrue(studyPlans.isEmpty());
    }

    @Test
    void save_ShouldAddStudyPlanSuccessfully() throws SQLException {
        // Act
        repository.save(testStudyPlan);

        // Assert
        List<StudyPlan> savedPlans = repository.findAllStudyPlans();
        assertEquals(1, savedPlans.size());
        assertEquals(testStudyPlan.getTitle(), savedPlans.get(0).getTitle());
    }

    @Test
    void delete_WithValidIds_ShouldRemoveStudyPlan() throws SQLException {
        // Arrange
        repository.save(testStudyPlan);

        // Act
        repository.delete(testStudyPlan.getStudyPlanId(), testStudyPlan.getStudentId());

        // Assert
        List<StudyPlan> remainingPlans = repository.findAllStudyPlans();
        assertTrue(remainingPlans.isEmpty());
    }

    @Test
    void delete_WithInvalidStudyPlanId_ShouldNotModifyRepository() throws SQLException {
        // Arrange
        repository.save(testStudyPlan);

        // Act
        repository.delete(-1, testStudyPlan.getStudentId());

        // Assert
        List<StudyPlan> remainingPlans = repository.findAllStudyPlans();
        assertEquals(1, remainingPlans.size());
    }

    @Test
    void delete_WithInvalidUserId_ShouldNotModifyRepository() throws SQLException {
        // Arrange
        repository.save(testStudyPlan);

        // Act
        repository.delete(testStudyPlan.getStudyPlanId(), -1);

        // Assert
        List<StudyPlan> remainingPlans = repository.findAllStudyPlans();
        assertEquals(1, remainingPlans.size());
    }

    // Test implementation of StudyPlanRepository for testing
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