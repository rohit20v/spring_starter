package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.ActorFilmDTO;
import it.objectmethod.esercizi.spring_starter.entity.ActorFilm;
import it.objectmethod.esercizi.spring_starter.util.BaseMapstructMapping;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public interface ActorFilmWithMapstruct extends BaseMapstructMapping<ActorFilmDTO, ActorFilm> {
    @Override
    List<ActorFilmDTO> toDTOs(List<ActorFilm> entity);

    @Override
    ActorFilmDTO toDTO(ActorFilm actorFilm);

    @AfterMapping
    default void afterToDto(ActorFilm af, @MappingTarget ActorFilmDTO dto) {
        // todo your code
    }
}