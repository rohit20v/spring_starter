package it.objectmethod.esercizi.spring_starter.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonView({FilmDTO.MinimalReq.class})
public class ActorFilmResponse {
    private Integer actorId;
    private String actorName;
    private String actorSurname;
    private String actorCity;
    private LocalDate actorDob;

    private List<FilmDTO> filmDTO;
}
