package it.objectmethod.esercizi.spring_starter.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomValidator implements ConstraintValidator<CustomAnnotation, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.toUpperCase().equals(value);

    }
}
