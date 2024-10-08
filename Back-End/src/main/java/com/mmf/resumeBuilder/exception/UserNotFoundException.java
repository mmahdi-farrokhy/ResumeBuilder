package com.mmf.resumeBuilder.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userEmail) {
        super("User with email " + userEmail + " not found");
    }
}
