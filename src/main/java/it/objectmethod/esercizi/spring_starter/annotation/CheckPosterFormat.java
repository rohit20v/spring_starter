package it.objectmethod.esercizi.spring_starter.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PosterFormatterChecker.class)
public @interface CheckPosterFormat {
    String message() default " format invalid!. Please provide a valid URL.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] allowedExtensions() default {"jpg", "png", "webp", "gif"};

    String[] disallowedExtensions() default {};

    String[] allowedMimeTypes() default {"image/jpeg", "image/png", "image/gif"};

    String[] disallowedMimeTypes() default {};
}
