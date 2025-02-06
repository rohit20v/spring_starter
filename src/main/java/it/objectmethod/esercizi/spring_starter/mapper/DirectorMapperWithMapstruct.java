package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.DirectorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Director;
import it.objectmethod.esercizi.spring_starter.util.BaseMapstructMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public interface DirectorMapperWithMapstruct extends BaseMapstructMapping<DirectorDTO, Director> {


    @Override
    @Mapping(target = "films", ignore = true)
    DirectorDTO toDTO(Director director);

    @Override
    @Mapping(target = "films", ignore = true)
    Director toEntity(DirectorDTO dto);
}
