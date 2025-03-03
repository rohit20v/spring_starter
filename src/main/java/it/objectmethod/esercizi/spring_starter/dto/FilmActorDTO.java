package it.objectmethod.esercizi.spring_starter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder(toBuilder = true)
public class FilmActorDTO {
    private Integer id;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date release_date;

    private String poster;

    private String category;

    private DirectorDTO director;

    private List<ActorDTO> actor;
}
