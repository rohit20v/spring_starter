package it.objectmethod.esercizi.spring_starter.specification;

import it.objectmethod.esercizi.spring_starter.dto.ActorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ActorSpecs {
    private static Integer name;

    public static Specification<Actor> toSpecification(ActorDTO actorDTO) {
        return Specification.
                <Actor>where(null)
                .and(findActorsFilmsById(name));
    }

    public static Specification<Actor> findByAllColumns(String name, String surname, Date dob, String city) {
        return (root, query, criteriaBuilder) -> {
            if (name == null && surname == null && dob == null && city == null)
                return null;
            Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
            Predicate surnamePredicate = criteriaBuilder.like(root.get("surname"), "%" + surname + "%");
            Predicate dobPredicate = criteriaBuilder.equal(
                    criteriaBuilder.function("date", Date.class, root.get("dob")), dob);

            Predicate cityPredicate = criteriaBuilder.like(root.get("city"), "%" + city + "%");

            return criteriaBuilder.or(namePredicate, surnamePredicate, dobPredicate, cityPredicate);

        };
    }

    public static Specification<Actor> findByAllColumns(ActorDTO dto) {
        return findByAllColumns(dto.getName(), dto.getSurname(), dto.getDob(), dto.getCity());
    }

    public static Specification<Actor> findActorsFilmsById(Integer id) {
        return (root, query, criteriaBuilder) -> {

            Join<Film, Actor> filmJoin = root.join("films");
            return criteriaBuilder.equal(filmJoin.get("id"), id);

        };

    }
}
