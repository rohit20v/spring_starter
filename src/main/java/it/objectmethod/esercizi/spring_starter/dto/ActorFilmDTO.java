package it.objectmethod.esercizi.spring_starter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActorFilmDTO {
    private Integer id_film;
    private Integer id_actor;
}
