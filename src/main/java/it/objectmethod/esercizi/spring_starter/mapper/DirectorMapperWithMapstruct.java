package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.DirectorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Director;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.util.BaseMapstructMapping;
import org.mapstruct.*;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DirectorMapperWithMapstruct extends BaseMapstructMapping<DirectorDTO, Director> {

    @Override
    @Mapping(target = "films", source = "films", qualifiedByName = "toIdFilm")
    DirectorDTO toDTO(Director director);

    @Override
    @Mapping(target = "films", source = "films", qualifiedByName = "toFilm")
    Director toEntity(DirectorDTO dto);

    @Named(value = "toIdFilm")
    default Integer toIdFilm(Film film) {
        return film.getId();
    }

    @Named(value = "toFilm")
    default Film toFilm(Integer id) {
        return Film.builder().id(id).build();
    }
}
