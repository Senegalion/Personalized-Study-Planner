//package org.example.personalizedstudyplanner.controllers;
//
//import javafx.application.Platform;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.ListView;
//import javafx.stage.Stage;
//import org.example.personalizedstudyplanner.models.StudyPlan;
//import org.example.personalizedstudyplanner.services.StudyPlanService;
//import org.junit.jupiter.api.*;
//
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.sql.SQLException;
//import java.time.OffsetDateTime;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class SelectPlannerControllerTest {
//    private SelectPlannerController controller;
//    private StudyPlanService mockStudyPlanService;
//    private Stage mockStage;
//    private ListView<StudyPlan> plannerListView;
//
//    @BeforeAll
//    static void initJavaFX() throws InterruptedException {
//        JavaFXTestUtil.initJavaFX();
//    }
//
//    @BeforeEach
//    void setUp() throws InterruptedException {
//        mockStudyPlanService = mock(StudyPlanService.class);
//        mockStage = mock(Stage.class);
//        plannerListView = new ListView<>();
//
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            controller = new SelectPlannerController();
//            try {
//                Field listViewField = SelectPlannerController.class.getDeclaredField("plannerListView");
//                listViewField.setAccessible(true);
//                listViewField.set(controller, plannerListView);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                fail("Failed to set plannerListView field: " + e.getMessage());
//            }
//            latch.countDown();
//        });
//        latch.await(5, TimeUnit.SECONDS);
//    }
//
//    @Test
//    void testInitialize_NoPlanners_ShouldShowAlert() throws SQLException, InterruptedException {
//        when(mockStudyPlanService.getAllStudyPlans()).thenReturn(Collections.emptyList());
//
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            try {
//                controller.initialize();
//                assertTrue(plannerListView.getItems().isEmpty());
//            } catch (SQLException e) {
//                fail("SQLException should not occur");
//            }
//            latch.countDown();
//        });
//        latch.await(5, TimeUnit.SECONDS);
//    }
//
//    @Test
//    void testInitialize_WithPlanners_ShouldPopulateListView() throws SQLException, InterruptedException {
//        StudyPlan plan1 = new StudyPlan(1, 101, "Plan 1", "Description 1", OffsetDateTime.now());
//        StudyPlan plan2 = new StudyPlan(2, 102, "Plan 2", "Description 2", OffsetDateTime.now());
//        when(mockStudyPlanService.getAllStudyPlans()).thenReturn(Arrays.asList(plan1, plan2));
//
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            try {
//                controller.initialize();
//                assertEquals(2, plannerListView.getItems().size());
//            } catch (SQLException e) {
//                fail("SQLException should not occur");
//            }
//            latch.countDown();
//        });
//        latch.await(5, TimeUnit.SECONDS);
//    }
//
//    @Test
//    void testHandleSelectPlanner_NoSelection_ShouldShowWarning() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            try {
//                controller.handleSelectPlanner(null);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            assertNull(plannerListView.getSelectionModel().getSelectedItem());
//            latch.countDown();
//        });
//        latch.await(5, TimeUnit.SECONDS);
//    }
//
//    @Test
//    void testHandleDeletePlanner_ValidSelection_ShouldRemovePlanner() throws InterruptedException, SQLException {
//        StudyPlan plan = new StudyPlan(1, 101, "Plan to Delete", "Description", OffsetDateTime.now());
//        ObservableList<StudyPlan> observableList = FXCollections.observableArrayList(plan);
//        plannerListView.setItems(observableList);
//        plannerListView.getSelectionModel().select(plan);
//
//        doNothing().when(mockStudyPlanService).deleteStudyPlan(1);
//
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            controller.handleDeletePlanner(null);
//            assertFalse(plannerListView.getItems().contains(plan));
//            latch.countDown();
//        });
//        latch.await(5, TimeUnit.SECONDS);
//    }
//
//    @Test
//    void testHandleDeletePlanner_NoSelection_ShouldShowWarning() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            controller.handleDeletePlanner(null);
//            assertTrue(plannerListView.getItems().isEmpty());
//            latch.countDown();
//        });
//        latch.await(5, TimeUnit.SECONDS);
//    }
//
////    @AfterAll
////    static void tearDown() {
////        JavaFXTestUtil.shutdownJavaFX();
////    }
//}
