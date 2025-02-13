package it.objectmethod.esercizi.spring_starter.specification;

import it.objectmethod.esercizi.spring_starter.dto.ActorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.entity.Director;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActorSpecs {
    private static Integer name;

    public static Specification<Actor> toSpecification(ActorDTO actorDTO) {
        return Specification.
                <Actor>where(null)
                .and(findActorsFilmsById(name));
    }

    public static Specification<Actor> findByAllColumns(String name, String surname, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dob, String city) {
        return (root, query, criteriaBuilder) -> {
            if (name == null && surname == null && dob == null && city == null)
                return null;
            List<Predicate> predicates = new ArrayList<>();
            if (name != null)
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            if (surname != null)
                predicates.add(criteriaBuilder.like(root.get("surname"), "%" + surname + "%"));
            if (dob != null)
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.function("date", LocalDate.class, root.get("dob")), dob));
            if (city != null)
                predicates.add(criteriaBuilder.like(root.get("city"), "%" + city + "%"));

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));

        };
    }

    public static Specification<Actor> findByAllColumns(ActorDTO dto) {
        return findByAllColumns(dto.getName(), dto.getSurname(), dto.getDob(), dto.getCity());
    }

    public static Specification<Actor> findActorsFilmsById(Integer id) {
        return (Root<Actor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            Join<Film, Actor> filmJoin = root.join("films");
            criteriaBuilder.createQuery(Director.class);

            return criteriaBuilder.equal(filmJoin.get("id"), id);

        };
    }

    public static Specification<Actor> findByActorNameIn(String[] names) {
        return (root, query, cb) -> root.get("name").in((Object[]) names);
    }
}