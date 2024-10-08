package com.mmf.resumeBuilder.validation;

import com.mmf.resumeBuilder.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NotDuplicatedEmailValidator implements ConstraintValidator<NotDuplicatedEmail, String> {
    @Autowired
    UserRepository testDAO;

    @Override
    public void initialize(NotDuplicatedEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return testDAO == null || !testDAO.existsByEmail(email);
    }
}
