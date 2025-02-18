package it.objectmethod.esercizi.spring_starter.service;

import it.objectmethod.esercizi.spring_starter.dto.auth.AuthorizationRequestDTO;
import it.objectmethod.esercizi.spring_starter.mapper.AuthorizationResponseMapper;
import it.objectmethod.esercizi.spring_starter.repository.AuthorizationRequestRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationResponseService {
    private final AuthorizationRequestRepo authorizationRequestRepo;
    private final AuthorizationResponseMapper authorizationResponseMapper;

    public AuthorizationResponseService(AuthorizationRequestRepo authorizationRequestRepo, AuthorizationResponseMapper authorizationResponseMapper) {
        this.authorizationRequestRepo = authorizationRequestRepo;
        this.authorizationResponseMapper = authorizationResponseMapper;
    }

    public AuthorizationRequestDTO save(AuthorizationRequestDTO authorizationRequest) {

        return authorizationResponseMapper.toDTO(
                authorizationRequestRepo.save(
                        authorizationResponseMapper.toEntity(
                                authorizationRequest
                        )
                ));
    }

    public AuthorizationRequestDTO login(final AuthorizationRequestDTO requestDto) {

        return authorizationRequestRepo.findByEmail(requestDto.email(), AuthorizationRequestDTO.class).get();
    }

    public List<AuthorizationRequestDTO> getAll() {
        return authorizationResponseMapper.toDTOs(
                authorizationRequestRepo.findAll()
        );
    }
}