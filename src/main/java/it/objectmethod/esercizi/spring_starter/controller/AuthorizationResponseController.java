package it.objectmethod.esercizi.spring_starter.controller;

import it.objectmethod.esercizi.spring_starter.auth.JwtTokenProvider;
import it.objectmethod.esercizi.spring_starter.dto.auth.AuthenticationResponseDTO;
import it.objectmethod.esercizi.spring_starter.dto.auth.AuthorizationRequestDTO;
import it.objectmethod.esercizi.spring_starter.service.AuthorizationResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationResponseController {

    private final AuthorizationResponseService authorizationResponseService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthorizationResponseController(AuthorizationResponseService authorizationResponseService, JwtTokenProvider jwtTokenProvider) {
        this.authorizationResponseService = authorizationResponseService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> post(@RequestBody @Validated AuthorizationRequestDTO request) {
        AuthorizationRequestDTO userFoundByEmail = authorizationResponseService.login(request);

        String token = jwtTokenProvider.generateToken(
                new AuthorizationRequestDTO(request.id(), request.name(), request.email())
        );

        return ResponseEntity.ok(AuthenticationResponseDTO.builder().email(userFoundByEmail.email()).username(userFoundByEmail.name()).token(token).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Validated AuthorizationRequestDTO request) {
        authorizationResponseService.save(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("allUsers")
    public ResponseEntity<List<AuthorizationRequestDTO>> get() {
        return ResponseEntity.ok(authorizationResponseService.getAll());
    }

}
