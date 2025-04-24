package org.example.personalizedstudyplanner.context;

public class StudyPlanContext {
    private static int currentStudyPlanId = -1;

    private StudyPlanContext() {
    }

    public static void setCurrentStudyPlanId(int studyPlanId) {
        currentStudyPlanId = studyPlanId;
    }

    public static int getCurrentStudyPlanId() {
        if (currentStudyPlanId == -1) {
            throw new IllegalStateException("No currently selected study plan");
        }
        return currentStudyPlanId;
    }

    public static void clear() {
        currentStudyPlanId = -1;
    }
}
