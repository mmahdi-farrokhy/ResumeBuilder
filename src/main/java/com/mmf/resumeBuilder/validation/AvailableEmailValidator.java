package com.mmf.resumeBuilder.validation;

import com.mmf.resumeBuilder.repository.AppUserDAO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class AvailableEmailValidator implements ConstraintValidator<AvailableEmail, String> {
    @Autowired
    AppUserDAO testDAO;

    @Override
    public void initialize(AvailableEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return testDAO == null || !testDAO.existsByEmail(email);
    }
}