package it.objectmethod.esercizi.spring_starter.repository;

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

    <T> Optional<T> findByCity(String city, Class<T> classname);

    @Query(value = "SELECT id, name FROM actors", nativeQuery = true)
    <T> List<T> findAll(Class<T> type);
//    @Query("select a.films from Actor a where a.id = :id")
//    Optional<List<Film>> getFilmsByActorID(Integer id);

    @Query("select a from Actor a where a.city = :city")
    Optional<List<Actor>> getActorsByCityJPQL(String city);

    @Query(value = "select * from actors where actors.city = :city", nativeQuery = true)
    Optional<List<Actor>> getActorsByCityNative(@Param("city") String city);

}
