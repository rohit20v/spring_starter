package it.objectmethod.esercizi.spring_starter.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthenticationResponseDTO (String token, String username) {
}
