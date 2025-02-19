package it.objectmethod.esercizi.spring_starter.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomValidator implements ConstraintValidator<CustomAnnotation, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().startsWith(" ") || value.trim().endsWith(" ")) {
            return true;
        }
        return value.toUpperCase().equals(value);

    }
}
