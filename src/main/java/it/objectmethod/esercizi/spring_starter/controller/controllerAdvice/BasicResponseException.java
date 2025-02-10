package it.objectmethod.esercizi.spring_starter.controller.controllerAdvice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public final class BasicResponseException extends RuntimeException {
    private String message;
    @Getter
    private final Instant timestamp;
    @Getter
    private final HttpStatus httpStatus;

    private BasicResponseException(String message, HttpStatus httpStatus) {
        super(message);
        this.timestamp = Instant.now();
        this.httpStatus = httpStatus;
    }

    private BasicResponseException(String message, Throwable cause, HttpStatus http) {
        super(message, cause);
        this.timestamp = Instant.now();
        this.httpStatus = http;
    }

    public static BasicResponseException notFoundException(String message, Object... params) {
        return new BasicResponseException(String
                .format(message, params), HttpStatus.NOT_FOUND);
    }

    public static BasicResponseException badRequestException(String message, Object... params) {
        return new BasicResponseException(String.format(message, params), HttpStatus.BAD_REQUEST);
    }

}

