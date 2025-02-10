package it.objectmethod.esercizi.spring_starter.repository;

import it.objectmethod.esercizi.spring_starter.dto.response.ActorFilmCombination;
import it.objectmethod.esercizi.spring_starter.entity.ActorFilm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActorFilmRepository extends JpaRepository<ActorFilm, Integer>, JpaSpecificationExecutor<ActorFilm> {
    @Query("""
            select new it.objectmethod.esercizi.spring_starter.dto.response.ActorFilmCombination
                        (a.id, a.name, a.surname, a.city, a.dob, f.id, f.title, f.category, f.date )
                         from Actor a join a.films  f
            """)
    List<ActorFilmCombination> getActorAndFilmInfo();
}
