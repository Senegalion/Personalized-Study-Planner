package org.example.personalizedstudyplanner.context;

public class UserContext {
    private static int currentUserId = -1;

    private UserContext() {
    }

    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

    public static int getCurrentUserId() {
        if (currentUserId == -1) {
            throw new IllegalStateException("No user is currently logged in.");
        }
        return currentUserId;
    }

    public static void clear() {
        currentUserId = -1;
    }
}
