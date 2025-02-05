package it.objectmethod.esercizi.spring_starter.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DirectorDTO {
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String city;

    private List<Integer> film = new ArrayList<>();
}
