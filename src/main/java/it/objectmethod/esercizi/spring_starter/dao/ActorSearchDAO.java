package it.objectmethod.esercizi.spring_starter.dao;

import it.objectmethod.esercizi.spring_starter.dto.ActorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.mapper.ActorMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorSearchDAO {

    private final EntityManager em;
    private final ActorMapper actorMapper;

    public ActorSearchDAO(EntityManager em, ActorMapper actorMapper) {
        this.em = em;
        this.actorMapper = actorMapper;
    }

    public List<ActorDTO> finalAll(String name, String surname, String city) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Actor> criteriaQuery = criteriaBuilder.createQuery(Actor.class);

        Root<Actor> root = criteriaQuery.from(Actor.class);

        Predicate firstName = criteriaBuilder
                .like(root.get("name"), "%" + name + "%");
        Predicate lastName = criteriaBuilder
                .like(root.get("surname"), "%" + surname + "%");
        Predicate inputCity = criteriaBuilder
                .like(root.get("city"), "%" + city + "%");

        Predicate orPredicate = criteriaBuilder
                .or(firstName, lastName, inputCity);

        criteriaQuery.where(orPredicate);

        TypedQuery<Actor> query = em.createQuery(criteriaQuery);
        return actorMapper.mapToDtos(query.getResultList());
    }
}
