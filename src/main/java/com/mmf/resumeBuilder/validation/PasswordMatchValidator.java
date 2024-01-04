package com.mmf.resumeBuilder.validation;

import com.mmf.resumeBuilder.model.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, User> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        String password = user.getPassword();
        String passwordConfirmation = user.getPasswordConfirmation();
        boolean isValid = hasValue(password) && hasValue(passwordConfirmation) && password.equals(passwordConfirmation);
        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("passwordConfirmation").addConstraintViolation();
        }

        return isValid;
    }

    private boolean hasValue(String string) {
        return string != null && string.trim() != "";
    }
}
