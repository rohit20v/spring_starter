package it.objectmethod.esercizi.spring_starter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record FilmRecord(
        Integer id,
        String title,
         String poster,
        Date date,
        String category,
//        DirectorDTO director
        @JsonProperty("idActor") Integer actor_id,
        String actor_city
) {
}
