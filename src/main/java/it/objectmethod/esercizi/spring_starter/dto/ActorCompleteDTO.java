package it.objectmethod.esercizi.spring_starter.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonView({FilmDTO.BasicView.class, FilmDTO.DetailedView.class})
public class ActorCompleteDTO {
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @NotNull
    private String city;

    private List<FilmDTO> films = new ArrayList<>();
}
