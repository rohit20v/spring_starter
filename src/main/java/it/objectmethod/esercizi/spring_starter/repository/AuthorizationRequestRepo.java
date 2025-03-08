package it.objectmethod.esercizi.spring_starter.repository;

import it.objectmethod.esercizi.spring_starter.entity.AuthorizationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizationRequestRepo extends JpaRepository<AuthorizationRequest, Integer>, JpaSpecificationExecutor<AuthorizationRequest> {

    <T> Optional<T> findByEmail(String email, Class<T> type);

    <T> Optional<T> findByEmailAndName(String email, String name, Class<T> type);

    Optional<AuthorizationRequest> findByEmailAndName(String email, String name);

    boolean existsAuthorizationRequestByEmail(String email);
}
