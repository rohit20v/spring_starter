package it.objectmethod.esercizi.spring_starter.util;

import java.util.List;
public interface BaseMapstructMapping <DTO, ENTITY>{

    DTO toDTO(ENTITY entity);

    List<DTO> toDTOs(List<ENTITY> entity);

    ENTITY toEntity(DTO dto);

    List<ENTITY> toEntities(List<DTO> dtos);
}
