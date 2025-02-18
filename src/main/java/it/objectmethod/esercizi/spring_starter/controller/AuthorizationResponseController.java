package it.objectmethod.esercizi.spring_starter.controller;

import it.objectmethod.esercizi.spring_starter.dto.auth.AuthenticationResponseDTO;
import it.objectmethod.esercizi.spring_starter.dto.auth.AuthorizationRequestDTO;
import it.objectmethod.esercizi.spring_starter.service.AuthorizationResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthorizationResponseController {

    private final AuthorizationResponseService authorizationResponseService;

    public AuthorizationResponseController(AuthorizationResponseService authorizationResponseService) {
        this.authorizationResponseService = authorizationResponseService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> post(@RequestBody @Validated AuthorizationRequestDTO request) {
        return ResponseEntity.ok(authorizationResponseService.login(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Validated AuthorizationRequestDTO request) {
        AuthorizationRequestDTO save = authorizationResponseService.save(request);
        return ResponseEntity.ok(Map.of("message", String.format("User: %s has been registered successfully", save.name())));
    }

    @GetMapping("allUsers")
    public ResponseEntity<List<AuthorizationRequestDTO>> get() {
        return ResponseEntity.ok(authorizationResponseService.getAll());
    }

}
