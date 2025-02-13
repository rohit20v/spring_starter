package it.objectmethod.esercizi.spring_starter.dto;

import java.util.Date;

public record FilmRecord(
        Integer id,
        String title,
        Date date,
        String category
//        List<ActorRecord> actor
) {
}
