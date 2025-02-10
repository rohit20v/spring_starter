package it.objectmethod.esercizi.spring_starter.annotation;

import it.objectmethod.esercizi.spring_starter.controller.controllerAdvice.BasicResponseException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class VerifyDate implements ConstraintValidator<AvoidFutureDate, Date> {

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        Date today = new Date();
        if (today.compareTo(value) <= 0) {
            throw BasicResponseException.badRequestException("Invalid Date: %s Please enter a valid date", value);
        }
        return today.compareTo(value) > 0;
    }
}
