package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.ActorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.util.BaseMapstructMapping;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public interface ActorMapperWIthMapstruct extends BaseMapstructMapping<ActorDTO, Actor> {

    @Mapping(target = "films", source = "films", qualifiedByName = "toIdFilm")
    ActorDTO toDTO(Actor actor);

    @Mapping(target = "films", source = "films", qualifiedByName = "toFilm")
    Actor toEntity(ActorDTO actorDTO);

    @Named(value = "toIdFilm")
    default Integer toIdFilm(Film film) {
        return film.getId();
    }

    @Named(value = "toFilm")
    default Film toFilm(Integer id) {
        return Film.builder().id(id).build();
    }

}