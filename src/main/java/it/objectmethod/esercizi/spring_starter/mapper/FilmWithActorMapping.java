package it.objectmethod.esercizi.spring_starter.mapper;


import it.objectmethod.esercizi.spring_starter.dto.FilmActorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.util.BaseMapstructMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = {DirectorMapperWithMapstruct.class, ActorMapperWIthMapstruct.class})
public interface FilmWithActorMapping extends BaseMapstructMapping<FilmActorDTO, Film> {

    @Override
    Film toEntity(FilmActorDTO filmActorDTO);

    @Override
    FilmActorDTO toDTO(Film film);
}
