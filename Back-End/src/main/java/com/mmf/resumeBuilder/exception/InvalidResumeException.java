package com.mmf.resumeBuilder.exception;

public class InvalidResumeException extends RuntimeException {
    public InvalidResumeException() {
        super("Resume must have a non-null user");
    }
}
