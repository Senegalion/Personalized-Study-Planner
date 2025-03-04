package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;

public class JavaFXTestUtil {
    private static boolean initialized = false;

    public static void initJavaFX() throws InterruptedException {
        if (!initialized) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(() -> {
                initialized = true;
                latch.countDown();
            });
            latch.await();
        }
    }
}