package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.ActorCompleteDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.util.BasicMethodMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {FilmMapperWithMapstruct.class})
public interface ActorCompleteMapstruct extends BasicMethodMapping<ActorCompleteDTO, Actor> {

}
