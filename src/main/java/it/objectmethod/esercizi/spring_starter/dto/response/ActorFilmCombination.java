package it.objectmethod.esercizi.spring_starter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActorFilmCombination {
    private Integer actorId;
    private String actorName;
    private String actorSurname;
    private String actorCity;
    private LocalDate actorDob;
    private Integer filmId;
    private String filmTitle;
    private String filmCategory;
    private Date releaseDate;
}