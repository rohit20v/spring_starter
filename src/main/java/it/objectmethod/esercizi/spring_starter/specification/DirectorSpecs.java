package it.objectmethod.esercizi.spring_starter.specification;

import it.objectmethod.esercizi.spring_starter.dto.DirectorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Director;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DirectorSpecs {

    public static Specification<Director> findByAllColumns(String name, String surname, String city) {

        return (root, query, criteriaBuilder) -> {
            Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
            Predicate surnamePredicate = criteriaBuilder.like(root.get("surname"), "%" + surname + "%");
            Predicate cityPredicate = criteriaBuilder.like(root.get("city"), "%" + city + "%");
            return criteriaBuilder.or(namePredicate, surnamePredicate, cityPredicate);
        };

    }


    public static Specification<Director> findByAllColumns(DirectorDTO dto) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + dto.getName() + "%"));
            }
            if (dto.getSurname() != null) {
                predicates.add(criteriaBuilder.like(root.get("surname"), "%" + dto.getSurname() + "%"));
            }
            if (dto.getCity() != null) {
                predicates.add(criteriaBuilder.like(root.get("city"), "%" + dto.getCity() + "%"));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

}
