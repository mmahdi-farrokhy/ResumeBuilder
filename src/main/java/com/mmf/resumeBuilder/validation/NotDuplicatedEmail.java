package com.mmf.resumeBuilder.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NotDuplicatedEmailValidator.class)
@Documented
public @interface NotDuplicatedEmail {
    String message() default "این ایمیل قبلا استفاده شده است";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
