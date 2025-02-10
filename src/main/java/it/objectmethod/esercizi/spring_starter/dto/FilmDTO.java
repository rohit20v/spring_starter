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

    @JsonView({MinimalReq.class})
    private Integer id;

    @NotNull
    @JsonView({MinimalReq.class})
    private String title;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonView({MinimalReq.class})
    private Date date;

    @NotNull
    @JsonView({MinimalReq.class})
    private String category;

    @JsonView({MinimalReq.class})
    private DirectorDTO director;

    private List<Integer> actors;

    public interface Extra {
    }

    public interface MinimalReq {
    }
}

