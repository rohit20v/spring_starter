package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import it.objectmethod.esercizi.spring_starter.dto.response.ActorFilmCombination;
import it.objectmethod.esercizi.spring_starter.dto.response.ActorFilmResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActorFilmMapper {

    public List<ActorFilmResponse> actorFilmResponseMapper(List<ActorFilmCombination> combination) {
        return combination.stream().map(this::actorFilmResponseMapper).toList();
    }

    private ActorFilmResponse actorFilmResponseMapper(ActorFilmCombination combination) {
        return ActorFilmResponse.builder()
                .actorId(combination.getActorId())
                .actorName(combination.getActorName())
                .actorSurname(combination.getActorSurname())
                .actorCity(combination.getActorCity())
                .actorDob(combination.getActorDob())
                .filmDTO(new ArrayList<>(List.of(filmMapper(combination))))
                .build();
    }

    public FilmDTO filmMapper(ActorFilmCombination actorFilmCombination) {
        return FilmDTO.builder()
                .id(actorFilmCombination.getFilmId())
                .title(actorFilmCombination.getFilmTitle())
                .release_date(actorFilmCombination.getReleaseDate())
                .category(actorFilmCombination.getFilmCategory())
                .build();
    }

}
