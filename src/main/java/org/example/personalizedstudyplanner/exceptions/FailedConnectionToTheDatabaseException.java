package org.example.personalizedstudyplanner.exceptions;

public class FailedConnectionToTheDatabaseException extends RuntimeException {
    public FailedConnectionToTheDatabaseException(String message) {
        super(message);
    }
}
