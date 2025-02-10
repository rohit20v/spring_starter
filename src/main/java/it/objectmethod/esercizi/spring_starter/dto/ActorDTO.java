package it.objectmethod.esercizi.spring_starter.dto;

import com.fasterxml.jackson.annotation.JsonView;
import it.objectmethod.esercizi.spring_starter.annotation.AvoidFutureDate;
import it.objectmethod.esercizi.spring_starter.annotation.CheckInputValidity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@CheckInputValidity
public class ActorDTO {

    @JsonView({FilmDTO.MinimalReq.class})
    private Integer id;

    @NotNull
    @JsonView({FilmDTO.MinimalReq.class})
    private String name;

    @NotNull
    @JsonView({FilmDTO.MinimalReq.class})
    private String surname;

    @NotNull
    @AvoidFutureDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonView({FilmDTO.MinimalReq.class})
    private Date dob;

    @NotNull
    @JsonView({FilmDTO.MinimalReq.class})
    private String city;

    private List<Integer> films = new ArrayList<>();
}
