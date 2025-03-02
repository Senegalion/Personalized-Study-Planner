package org.example.personalizedstudyplanner.models;

public enum AssignmentStatus {
    PENDING,
    COMPLETED,
    OVERDUE;

    public double getProgressValue() {
        return switch (this) {
            case COMPLETED -> 1.0;
            case OVERDUE -> 0.5;
            default -> 0.0;
        };
    }
}
