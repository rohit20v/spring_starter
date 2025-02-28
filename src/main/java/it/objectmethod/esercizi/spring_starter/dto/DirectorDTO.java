package it.objectmethod.esercizi.spring_starter.dto;


import com.fasterxml.jackson.annotation.JsonView;
import it.objectmethod.esercizi.spring_starter.annotation.CustomAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
@JsonView({FilmDTO.BasicView.class, FilmDTO.DetailedView.class})
public class DirectorDTO {
    //    @NotNull(groups = {PUT.class, Default.class})
    private Integer id;

    @NotEmpty
    @NotBlank
    @CustomAnnotation(message = "ain't uppercase")
    private String name;

    @NotEmpty
    private String surname;

    @NotEmpty
    private String city;
    @JsonView({FilmDTO.Extra.class})

    private List<Integer> films = new ArrayList<>();

}


