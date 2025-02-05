package it.objectmethod.esercizi.spring_starter.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ActorDTO {
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

    @NotNull
    private List<Integer> films;
}
