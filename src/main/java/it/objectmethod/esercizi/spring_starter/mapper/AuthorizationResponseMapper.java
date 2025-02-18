package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.auth.AuthorizationRequestDTO;
import it.objectmethod.esercizi.spring_starter.entity.AuthorizationRequest;
import it.objectmethod.esercizi.spring_starter.util.BaseMapstructMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AuthorizationResponseMapper extends BaseMapstructMapping<AuthorizationRequestDTO, AuthorizationRequest> {
}
