package it.objectmethod.esercizi.spring_starter.util;

import java.util.List;

public interface BasicMethodMapping<DTO, ENTITY> {
    DTO mapToDto(ENTITY entity);
    ENTITY mapToEntity(DTO dto);//da repository verso il return del metodo nel serivce
    List<ENTITY> mapToEntities(List<DTO> dtos);// tips -> return dtos.stream.map(this::mapToEntities).toList();
    List<DTO> mapToDtos(List<ENTITY> entities);
}
