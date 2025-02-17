package it.objectmethod.esercizi.spring_starter.dto;


import com.fasterxml.jackson.annotation.JsonView;
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
@JsonView({FilmDTO.MinimalReq.class})
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
    @JsonView({FilmDTO.Extra.class})

    private List<Integer> films = new ArrayList<>();
}
