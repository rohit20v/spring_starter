package it.objectmethod.esercizi.spring_starter.controller.controllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

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

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorBody> handleValidation(MethodArgumentNotValidException e) {
        List<String> errorList = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .toList();
        final ErrorBody errorBody = ErrorBody.builder()
                .message(String.join(",", errorList))
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(errorBody, e.getStatusCode());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorBody> handleValidation(Exception e) {

        final ErrorBody errorBody = ErrorBody.builder()
                .message(e.getMessage())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorBody> handleValidation(EntityNotFoundException e) {
        val i = e.getMessage().indexOf("y.");
        String err = "Unable to find " + e.getMessage().substring(i + 2);

        final ErrorBody errorBody = ErrorBody.builder()
                .message(err)
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorBody> handleValidation(SQLIntegrityConstraintViolationException e) {
        String invalidForeignKey = null;
        if (e.getErrorCode() == 1452) {
            invalidForeignKey = e.getMessage().substring(e.getMessage().indexOf("FOREIGN KEY"), e.getMessage().indexOf("REFERENCES"));
        }
        final ErrorBody errorBody = ErrorBody.builder()
                .message(
                        invalidForeignKey != null ?
                                "Foreign key violation: Invalid" + invalidForeignKey :
                                e.getLocalizedMessage().contains("email") ? "User already exists" : e.getLocalizedMessage())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}
