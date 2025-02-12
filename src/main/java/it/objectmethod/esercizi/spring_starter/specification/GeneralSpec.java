package it.objectmethod.esercizi.spring_starter.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GeneralSpec {
    public static <T> Specification<T> findByAllParams(final Map<String, String> map) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    try {
                        Date dateValue = dateFormat.parse(entry.getValue());

                        predicates.add(cb.equal(root.get(entry.getKey()), dateValue));
                    } catch (ParseException e) {
                        predicates.add(cb.like(root.get(entry.getKey()), "%" + entry.getValue() + "%"));
                    }
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
