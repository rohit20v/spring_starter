package it.objectmethod.esercizi.spring_starter.controller.controllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErrorBody {
    private String message;
    private Instant timestamp;
}
