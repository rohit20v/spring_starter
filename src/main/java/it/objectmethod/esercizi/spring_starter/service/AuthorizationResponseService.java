package it.objectmethod.esercizi.spring_starter.service;

import it.objectmethod.esercizi.spring_starter.auth.JwtTokenProvider;
import it.objectmethod.esercizi.spring_starter.dto.auth.AuthenticationResponseDTO;
import it.objectmethod.esercizi.spring_starter.dto.auth.AuthorizationRequestDTO;
import it.objectmethod.esercizi.spring_starter.mapper.AuthorizationResponseMapper;
import it.objectmethod.esercizi.spring_starter.repository.AuthorizationRequestRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthorizationResponseService {
    private final AuthorizationRequestRepo authorizationRequestRepo;
    private final AuthorizationResponseMapper authorizationResponseMapper;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthorizationResponseService(AuthorizationRequestRepo authorizationRequestRepo, AuthorizationResponseMapper authorizationResponseMapper, JwtTokenProvider jwtTokenProvider) {
        this.authorizationRequestRepo = authorizationRequestRepo;
        this.authorizationResponseMapper = authorizationResponseMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthorizationRequestDTO save(AuthorizationRequestDTO authorizationRequest) {

        return authorizationResponseMapper.toDTO(
                authorizationRequestRepo.save(
                        authorizationResponseMapper.toEntity(
                                authorizationRequest
                        )
                ));
    }

    public AuthenticationResponseDTO login(final AuthorizationRequestDTO requestDto) {
        AuthorizationRequestDTO userFoundByEmail = authorizationResponseMapper.toDTO(
                authorizationRequestRepo.findByEmailAndName
                                (requestDto.getEmail(), requestDto.getName())
                        .orElseThrow(() -> new NoSuchElementException(
                                String.format("User with email: %s and name: %s not found",
                                        requestDto.getEmail(), requestDto.getName()))
                        )
        );

        String token = jwtTokenProvider.generateToken(
                new AuthorizationRequestDTO(userFoundByEmail.getId(), userFoundByEmail.getName(), userFoundByEmail.getEmail(), userFoundByEmail.getRole()));

        return AuthenticationResponseDTO.builder().username(userFoundByEmail.getName()).token(token).build();

    }

    public List<AuthorizationRequestDTO> getAll() {
        return authorizationResponseMapper.toDTOs(
                authorizationRequestRepo.findAll()
        );
    }
}