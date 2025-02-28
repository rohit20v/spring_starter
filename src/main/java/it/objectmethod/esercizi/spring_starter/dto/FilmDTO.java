package it.objectmethod.esercizi.spring_starter.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmDTO {

    @JsonView({BasicView.class, DetailedView.class})
    private Integer id;

    @NotNull
    @JsonView({BasicView.class, DetailedView.class})
    private String title;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonView({BasicView.class, DetailedView.class})
    private Date release_date;

    @NotNull
    @JsonView({BasicView.class, DetailedView.class})
    private String category;

    @JsonView({DetailedView.class})
    private DirectorDTO director;

    private List<Integer> actors;

    public interface BasicView {}
    public interface DetailedView {}
    public interface Extra {}
}
