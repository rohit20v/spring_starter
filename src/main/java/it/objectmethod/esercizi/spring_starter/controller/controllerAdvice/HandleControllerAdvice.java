package it.objectmethod.esercizi.spring_starter.controller.controllerAdvice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // AOP -> Aspect Oriented Programming
public class HandleControllerAdvice {

    @ExceptionHandler({BasicResponseException.class})
    public ResponseEntity<ErrorBody> handleNullPointer(BasicResponseException e) {
        final ErrorBody erroBody = ErrorBody.builder()
                .message(e.getMessage())
                .timestamp(e.getTimestamp())
                .build();
        return new ResponseEntity<>(erroBody, e.getHttpStatus());
    }
}
