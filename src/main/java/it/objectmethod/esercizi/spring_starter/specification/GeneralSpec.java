package it.objectmethod.esercizi.spring_starter.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneralSpec {
    public static <T> Specification<T> findByAllParams(final Map<String, String> map) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    predicates.add(cb.like(root.get(entry.getKey()), "%" + entry.getValue() + "%"));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
