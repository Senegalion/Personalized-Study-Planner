package org.example.personalizedstudyplanner;

import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PersonalizedStudyPlannerApplicationTest {

    @Test
    void testMain() {
        assertDoesNotThrow(() -> {
            new Thread(() -> {
                PersonalizedStudyPlannerApplication.main(new String[]{});
                Platform.runLater(() -> Platform.exit());
            }).start();
        });
    }
}