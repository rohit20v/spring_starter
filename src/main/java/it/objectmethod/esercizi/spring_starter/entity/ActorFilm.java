package it.objectmethod.esercizi.spring_starter.entity;

import it.objectmethod.esercizi.spring_starter.dto.ActorFilmDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ActorFilmDTO.class)
@Builder
@Table(name = "film_actor")
@Entity
public class ActorFilm {

    @Id
    @Column(name = "id_film")
    private Integer id_film;

    @Id
    @Column(name = "id_actor")
    private Integer id_actor;
}
