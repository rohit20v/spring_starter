package it.objectmethod.esercizi.spring_starter.service;

import it.objectmethod.esercizi.spring_starter.dto.response.ActorFilmResponse;
import it.objectmethod.esercizi.spring_starter.mapper.ActorFilmMapper;
import it.objectmethod.esercizi.spring_starter.repository.ActorFilmRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ActorFilmService {


    private final ActorFilmRepository actorFilmRepository;
    private final ActorFilmMapper actorFilmMapper;

    public ActorFilmService(final ActorFilmRepository actorFilmRepository, ActorFilmMapper actorFilmMapper) {
        this.actorFilmRepository = actorFilmRepository;
        this.actorFilmMapper = actorFilmMapper;
    }

    public HashMap<Integer, ActorFilmResponse> getAllActorFilmResponse() {

        HashMap<Integer, ActorFilmResponse> map = new HashMap<>();

        List<ActorFilmResponse> list = actorFilmMapper.actorFilmResponseMapper(actorFilmRepository.getActorAndFilmInfo());

        list.forEach(i -> {
            ActorFilmResponse actorFilmResponse = map.get(i.getActorId());
            if (actorFilmResponse == null) {
                map.put(i.getActorId(), i);
            } else {
                actorFilmResponse.getFilmDTO().addAll(i.getFilmDTO());
            }
        });

        return map;
    }

}
