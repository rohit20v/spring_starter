package it.objectmethod.esercizi.spring_starter.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PosterFormatterChecker implements ConstraintValidator<CheckPosterFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.isEmpty()) {
            return true;
        }
        return value.endsWith("png") || value.endsWith("jpg") || value.endsWith("webp") || value.endsWith("gif");
    }
}
