package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.FilmUpdateDTO;
import it.objectmethod.esercizi.spring_starter.entity.Director;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.util.BaseMapstructMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",
        uses = {ActorMapperWIthMapstruct.class, DirectorMapperWithMapstruct.class},
        builder = @Builder(disableBuilder = true))
public interface FilmUpdateMapping extends BaseMapstructMapping<FilmUpdateDTO, Film> {

    @Override
    @Mapping(target = "director", source = "director.id")
//    @Mapping(target = "actor", source = "actor", qualifiedByName = "toActorIdList")
    FilmUpdateDTO toDTO(Film film);

    @Override
    @Mapping(target = "director", source = "director", qualifiedByName = "toDirector")
//    @Mapping(target = "actor", source = "actor", qualifiedByName = "toActorList")
    Film toEntity(FilmUpdateDTO filmUpdateDTO);

    @Named(value = "toDirector")
    default Director toDirector(Integer id) {
        return Director.builder().id(id).build();
    }

//    @Named(value = "toActorList")
//    default List<Actor> toActorList(List<Integer> actorIds) {
//        if (actorIds == null) return null;
//        return actorIds.stream().map(id -> Actor.builder().id(id).build()).toList();
//    }
//
//    @Named(value = "toActorIdList")
//    default List<Integer> toActorIdList(List<Actor> actors) {
//        if (actors == null) return null;
//        return actors.stream().map(Actor::getId).toList();
//    }
}
