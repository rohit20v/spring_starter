package it.objectmethod.esercizi.spring_starter.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorFilmDTO {
    @NotNull
    ActorDTO actorDTO;

    @NotNull
    FilmDTO filmDTO;
}
