package it.objectmethod.esercizi.spring_starter.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CustomValidator.class)
public @interface CustomAnnotation {

    String message() default "";

    Class<?>[] groups() default {};

    //must with @Constraint
    Class<? extends Payload>[] payload() default {};

}
