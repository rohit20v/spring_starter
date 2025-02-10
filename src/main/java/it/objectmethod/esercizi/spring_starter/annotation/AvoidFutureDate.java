package it.objectmethod.esercizi.spring_starter.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {VerifyDate.class})
public @interface AvoidFutureDate {

    String message() default "Invalid date! Please add a valid date of birth";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
