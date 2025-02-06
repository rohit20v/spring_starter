package it.objectmethod.esercizi.spring_starter.annotation;

import it.objectmethod.esercizi.spring_starter.dto.ActorDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InputValidator implements ConstraintValidator<CheckInputValidity, ActorDTO> {

    // Function, Consumer, Supplier, Predicate
    @Override
    public boolean isValid(ActorDTO value, ConstraintValidatorContext context) {
        return value.getName() != null && value.getSurname() != null &&
               value.getDob() != null && value.getCity() != null;
    }
}
