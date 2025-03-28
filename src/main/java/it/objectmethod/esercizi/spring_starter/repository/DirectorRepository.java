package it.objectmethod.esercizi.spring_starter.repository;

import it.objectmethod.esercizi.spring_starter.entity.Director;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DirectorRepository extends JpaRepository<Director, Integer>, JpaSpecificationExecutor<Director> {
    Director getDirectorsById(Integer id);

    @Query(value = "SELECT id, name FROM directors", nativeQuery = true)
    <T> List<T> getMeAll(Class<T> classCompiled);

    @Query(value = "SELECT film.id, film.title from film inner join directors on film.id_director = directors.id WHERE directors.id = :id", nativeQuery = true)
    <T> List<T> findFilmByDirectorId(Integer id, Class<T> classCompiled);

    interface Specs {
        static Specification<Director> findAllDirectorsWithFilteredName(String name) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("name"), "%" + name + "%");
        }

        static Specification<Director> findAllDirectorsWithFilteredSurname(String surname) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("surname"), "%" + surname + "%");
        }
    }
}

//        static Specification<Director> findByAllParams(final Map<String, String> map) {
//            return (root, query, cb) -> {
//
//                List<Predicate> predicates = new ArrayList<>();
//
//                for (Map.Entry<String, String> entry : map.entrySet()) {
//                    if (entry.getKey() != null && entry.getValue() != null) {
//                        predicates.add(cb.like(root.get(entry.getKey()), "%" + entry.getValue() + "%"));
//                    }
//                }
//                return cb.or(predicates.toArray(new Predicate[0]));
//            };
//        }
