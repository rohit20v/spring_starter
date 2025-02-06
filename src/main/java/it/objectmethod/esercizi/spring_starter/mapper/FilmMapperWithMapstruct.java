package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.util.BaseMapstructMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public interface FilmMapperWithMapstruct extends BaseMapstructMapping<FilmDTO, Film> {

    @Override
    @Mapping(target = "actor", source = "actors", ignore = true)
    @Mapping(target = "director", ignore = true)
    Film toEntity(FilmDTO filmDTO);


    @Override
    @Mapping(target = "director", ignore = true)
    @Mapping(target = "actors", source = "actor", ignore = true)
    FilmDTO toDTO(Film film);
}
