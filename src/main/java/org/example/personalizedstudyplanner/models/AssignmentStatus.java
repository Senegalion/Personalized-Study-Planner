package org.example.personalizedstudyplanner.models;

import java.util.Locale;

public enum AssignmentStatus {
    PENDING("Pending", "Oczekujące"),
    COMPLETED("Completed", "Zakończone"),
    OVERDUE("Overdue", "Przeterminowane");

    private final String enLabel;
    private final String plLabel;

    AssignmentStatus(String enLabel, String plLabel) {
        this.enLabel = enLabel;
        this.plLabel = plLabel;
    }

    public String getLabel(Locale locale) {
        if (locale.getLanguage().equals("pl")) {
            return plLabel;
        } else {
            return enLabel;
        }
    }

    public double getProgressValue() {
        return switch (this) {
            case COMPLETED -> 1.0;
            case OVERDUE -> 0.5;
            default -> 0.0;
        };
    }
}
