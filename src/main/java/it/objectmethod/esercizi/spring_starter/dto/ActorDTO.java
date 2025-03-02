package it.objectmethod.esercizi.spring_starter.dto;

import com.fasterxml.jackson.annotation.JsonView;
import it.objectmethod.esercizi.spring_starter.annotation.CheckInputValidity;
import jakarta.validation.constraints.NotEmpty;
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
    @NotNull
    @JsonView({FilmDTO.BasicView.class})
    private Integer id;

    @NotEmpty(groups = {ForPost.class})
    @JsonView({FilmDTO.BasicView.class})
    private String name;

    @NotEmpty(groups = {ForPost.class})
    @JsonView({FilmDTO.BasicView.class})
    private String surname;

    @NotNull(groups = {ForPost.class})
//    @AvoidFutureDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonView({FilmDTO.BasicView.class})
    private LocalDate dob;

    @NotEmpty(groups = {ForPost.class})
    @JsonView({FilmDTO.BasicView.class})
    private String city;

    private List<Integer> films = new ArrayList<>();

    public interface ForPost {
    }
}
