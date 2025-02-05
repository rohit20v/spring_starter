package it.objectmethod.esercizi.spring_starter.specification;


import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class FilmSpecs {
    public static Specification<Film> findByAllColumns(String title, Date date, String category) {
        return (root, query, criteriaBuilder) -> {
            Predicate titlePredicate = criteriaBuilder.like(root.get("title"), "%" + title + "%");
            Predicate datePredicate = criteriaBuilder.equal(
                    criteriaBuilder.function("date", Date.class, root.get("date")),
                    date
            );
            Predicate categoryPredicate = criteriaBuilder.like(root.get("category"), "%" + category + "%");

            return criteriaBuilder.or(titlePredicate, datePredicate, categoryPredicate);

        };
    }

    public static Specification<Film> findByAllColumns(FilmDTO filmDTO) {
        return findByAllColumns(filmDTO.getTitle(), filmDTO.getDate(), filmDTO.getCategory());
    }

    public static Specification<Film> findFilmsByActorId(Integer id) {
        return (root, query, cb) -> {
            Join<Actor, Film> actorJoin = root.join("actor");
            return cb.equal(actorJoin.get("id"), id);
        };
    }

    public static Specification<Film> findMoviesByDirectorID(Integer id) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("director").get("id"), id);
    }
}