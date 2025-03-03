package it.objectmethod.esercizi.spring_starter.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.groups.Default;
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
    @NotNull(groups = {Default.class})
    private Integer id;

    @NotEmpty(groups = {Default.class, IgnoreId.class})
    private String title;

    @NotNull(groups = {Default.class, IgnoreId.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(groups = {Default.class, IgnoreId.class})
    private Date release_date;

    private String poster;

    @NotEmpty(groups = {Default.class, IgnoreId.class})
    private String category;

    @NotNull(groups = {Default.class, IgnoreId.class})
    private Integer director;
//
//    @NotEmpty
//    private List<Integer> actor;

    public interface IgnoreId {
    }

}
