package it.objectmethod.esercizi.spring_starter.controller.controllerAdvice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public class UnauthorizedException extends RuntimeException {
    @Getter
    private final Instant timestamp;
    @Getter
    private final HttpStatus httpStatus;
    private String message;

    public UnauthorizedException(String message, HttpStatus httpStatus) {
        super(message);
        this.timestamp = Instant.now();
        this.httpStatus = httpStatus;
    }
}
