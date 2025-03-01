package it.objectmethod.esercizi.spring_starter.repository;

import it.objectmethod.esercizi.spring_starter.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film> {
    Film getFilmById(Integer id);

    @Query(value = "select id, title from film", nativeQuery = true)
    <T> List<T> findAll(Class<T> compliedClass);

    <T> List<T> getFilmByCategory(String category, Class<T> classCompiled);
}
