package it.objectmethod.esercizi.spring_starter.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmUpdateDTO {
    @NotNull
    private Integer id;

    @NotEmpty
    private String title;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent()
    private Date release_date;

    @NotEmpty
    private String category;

    @NotNull
    private Integer director;
//
//    @NotEmpty
//    private List<Integer> actor;
}

