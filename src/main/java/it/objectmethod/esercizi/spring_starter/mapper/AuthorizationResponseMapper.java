package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.auth.AuthorizationRequestDTO;
import it.objectmethod.esercizi.spring_starter.entity.AuthorizationRequest;
import it.objectmethod.esercizi.spring_starter.entity.Role;
import it.objectmethod.esercizi.spring_starter.util.BaseMapstructMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface AuthorizationResponseMapper extends BaseMapstructMapping<AuthorizationRequestDTO, AuthorizationRequest> {

    @Override
    @Mapping(target = "role", source = "role", qualifiedByName = "toRoleId")
    AuthorizationRequestDTO toDTO(AuthorizationRequest authorizationRequest);

    @Override
    @Mapping(target = "role", source = "role", qualifiedByName = "toRole")
    AuthorizationRequest toEntity(AuthorizationRequestDTO authorizationRequestDTO);

    @Named("toRoleId")
    default List<Integer> toRoleId(List<Role> roles) {
        return roles == null ? null : roles.stream()
                .map(Role::getId)
                .toList();
    }

    @Named("toRole")
    default List<Role> toRole(List<Integer> ids) {
        return ids == null ? null : ids.stream()
                .map(id -> Role.builder().id(id).build())
                .toList();
    }
}
