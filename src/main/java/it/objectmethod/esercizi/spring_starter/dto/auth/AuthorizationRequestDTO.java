package it.objectmethod.esercizi.spring_starter.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationRequestDTO {
    private Integer id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    private List<Integer> role = List.of();
}
