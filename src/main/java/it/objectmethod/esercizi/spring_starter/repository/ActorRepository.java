package it.objectmethod.esercizi.spring_starter.repository;

import it.objectmethod.esercizi.spring_starter.dto.ActorFilmDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor>, PagingAndSortingRepository<Actor, Integer> {
    Optional<Actor> findByName(String name);

    Optional<Actor> findByCity(String city);

//    @Query("select a.films from Actor a where a.id = :id")
//    Optional<List<Film>> getFilmsByActorID(Integer id);

    @Query("select a from Actor a where a.city = :city")
    Optional<List<Actor>> getActorsByCityJPQL(String city);

    @Query(value = "select * from actors where actors.city = :city", nativeQuery = true)
    Optional<List<Actor>> getActorsByCityNative(@Param("city") String city);

    @Query(value = "select * from actors " +
                   "join film_actor on actors.id = film_actor.id_actor" +
                   "join film on film_actor.id_film = film.id",
            nativeQuery = true)
    Optional<ActorFilmDTO> getAllFilmActorInfo();

}
