package it.objectmethod.esercizi.spring_starter.dto;

import lombok.Builder;

@Builder
public record IdNameActorDTO(Integer id, String name) {
}
