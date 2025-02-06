package it.objectmethod.esercizi.spring_starter.dto;


import it.objectmethod.esercizi.spring_starter.annotation.CustomAnnotation;
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
//    @NotNull(groups = {PUT.class, Default.class})
    private Integer id;

//    @NotNull
    @CustomAnnotation(message = "ain't uppercase")
    private String name;

//    @NotNull
    private String surname;

//    @NotNull
    private String city;

    private List<Integer> films = new ArrayList<>();
}
