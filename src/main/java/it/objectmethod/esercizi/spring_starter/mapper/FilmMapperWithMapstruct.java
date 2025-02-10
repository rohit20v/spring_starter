package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.util.BaseMapstructMapping;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = DirectorMapperWithMapstruct.class)
public interface FilmMapperWithMapstruct extends BaseMapstructMapping<FilmDTO, Film> {

    @Override
    @Mapping(target = "actor", source = "actors", qualifiedByName = "toActor")
    Film toEntity(FilmDTO filmDTO);

    @Override
    @Mapping(target = "actors", source = "actor", qualifiedByName = "toIdActor")
    FilmDTO toDTO(Film film);

    @Named(value = "toIdActor")
    default Integer toIdActor(Actor actor) {
        return actor.getId();
    }

    @Named(value = "toActor")
    default Actor toActor(Integer id) {
        return Actor.builder().id(id).build();
    }
}
