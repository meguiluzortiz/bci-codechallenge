package com.example.bci.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = PropertyPatternValidator.class)
public @interface PropertyPattern {
    String property();
    String message() default "{validator.property-pattern.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
