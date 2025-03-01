package it.objectmethod.esercizi.spring_starter.dto;

import lombok.Builder;

@Builder
public record IdNameDTO(Integer id, String name) {
}
