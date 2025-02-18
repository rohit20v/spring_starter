package it.objectmethod.esercizi.spring_starter.dto.auth;

import lombok.Builder;

@Builder
public record AuthenticationResponseDTO (String token, String username, String email){
}
