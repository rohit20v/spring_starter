package it.objectmethod.esercizi.spring_starter.dto;

import com.fasterxml.jackson.annotation.JsonView;
import it.objectmethod.esercizi.spring_starter.annotation.CheckInputValidity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@CheckInputValidity
public class ActorDTO {

    @JsonView({FilmDTO.BasicView.class})
    private Integer id;

    @NotNull
    @JsonView({FilmDTO.BasicView.class})
    private String name;

    @NotNull
    @JsonView({FilmDTO.BasicView.class})
    private String surname;

    @NotNull
//    @AvoidFutureDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonView({FilmDTO.BasicView.class})
    private LocalDate dob;

    @NotNull
    @JsonView({FilmDTO.BasicView.class})
    private String city;

    private List<Integer> films = new ArrayList<>();
}
