package com.mmf.resumeBuilder.exception;

public class ResumeNotFoundException extends RuntimeException {
    public ResumeNotFoundException(Integer resumeId) {
        super("Resume with id " + resumeId + " not found");
    }
}
