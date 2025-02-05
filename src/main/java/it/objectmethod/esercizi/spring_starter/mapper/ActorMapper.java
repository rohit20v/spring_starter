package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.ActorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.util.BasicMethodMapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActorMapper implements BasicMethodMapping<ActorDTO, Actor> {
    @Override
    public ActorDTO mapToDto(Actor actor) {
        List<Integer> filmIds = new ArrayList<Integer>();
        if (actor != null && actor.getFilms() != null) {

            filmIds = actor.getFilms().stream()
                    .map(Film::getId)
                    .toList();
            return ActorDTO.builder()
                    .id(actor.getId())
                    .name(actor.getName())
                    .surname(actor.getSurname())
                    .dob(actor.getDob())
                    .city(actor.getCity())
                    .films(filmIds)
                    .build();
        }
        return null;
    }

    @Override
    public Actor mapToEntity(ActorDTO actorDTO) {

        List<Film> films = new ArrayList<>();
        if (actorDTO.getFilms() != null) {
            films = actorDTO.getFilms().stream()
                    .map(v -> Film.builder().id(v).build())
                    .toList();
        }

        return Actor.builder()
                .id(actorDTO.getId())
                .name(actorDTO.getName())
                .surname(actorDTO.getSurname())
                .dob(actorDTO.getDob())
                .city(actorDTO.getCity())
                .films(films)
                .build();
    }

    @Override
    public List<Actor> mapToEntities(List<ActorDTO> actorDTOS) {
        return actorDTOS.stream()
                .map(this::mapToEntity)
                .toList();
    }

    @Override
    public List<ActorDTO> mapToDtos(List<Actor> actors) {
        if (actors != null) {
            return actors.stream()
                    .map(this::mapToDto)
                    .toList();

        }
        return null;
    }
}
