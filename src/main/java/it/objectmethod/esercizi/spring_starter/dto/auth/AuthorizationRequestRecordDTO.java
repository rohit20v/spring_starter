package it.objectmethod.esercizi.spring_starter.dto.auth;


import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;


@Builder
public record AuthorizationRequestRecordDTO(
        Integer id,

        @NotEmpty
        String name,

        @NotEmpty
        String email) {
}
