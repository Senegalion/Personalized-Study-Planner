package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateStudyPlanControllerTest {
    private CreateStudyPlanController controller;


    @BeforeEach
    void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {
            controller = new CreateStudyPlanController();
            controller.setTitleField(new TextField());
            controller.setDescriptionField(new TextArea());
            latch.countDown();
        });

        latch.await(5, TimeUnit.SECONDS);
    }


    @Test
    void testHandleCreateStudyPlan_EmptyFields_ShouldShowError() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.getTitleField().setText("");
            controller.getDescriptionField().setText("");

            assertTrue(controller.getTitleField().getText().isEmpty());
            assertTrue(controller.getDescriptionField().getText().isEmpty());

            latch.countDown();
        });


        latch.await(5, TimeUnit.SECONDS);
    }
}

