package org.example.personalizedstudyplanner.controllers;

import javafx.application.Platform;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class JavaFXTestUtil {
    private static boolean isJavaFXInitialized = false;
    private static final CountDownLatch startupLatch = new CountDownLatch(1);

    public static synchronized void initJavaFX() throws InterruptedException {
        if (Platform.isFxApplicationThread() || isJavaFXInitialized) {
            return; // Avoid re-initializing JavaFX
        }

        Thread fxThread = new Thread(() -> {
            try {
                Platform.startup(startupLatch::countDown);
            } catch (IllegalStateException e) {
                startupLatch.countDown(); // Ignore if already initialized
            }
        });

        fxThread.setDaemon(true);
        fxThread.start();

        if (!startupLatch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("JavaFX initialization timed out");
        }

        isJavaFXInitialized = true;
    }

    public static synchronized void shutdownJavaFX() {
        if (isJavaFXInitialized) {
            Platform.runLater(Platform::exit);
            isJavaFXInitialized = false;
        }
    }
}
