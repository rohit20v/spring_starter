package it.objectmethod.esercizi.spring_starter.dto.auth;


import jakarta.validation.constraints.NotEmpty;


public record AuthorizationRequestDTO(
        Integer id,

        @NotEmpty
        String name,

        @NotEmpty
        String email) {
}
