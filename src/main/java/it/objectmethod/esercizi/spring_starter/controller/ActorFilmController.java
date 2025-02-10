package it.objectmethod.esercizi.spring_starter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import it.objectmethod.esercizi.spring_starter.dto.response.ActorFilmResponse;
import it.objectmethod.esercizi.spring_starter.service.ActorFilmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/actor_film")
public class ActorFilmController {


    private final ActorFilmService actorFilmService;

    public ActorFilmController(ActorFilmService actorFilmService) {
        this.actorFilmService = actorFilmService;
    }

    @JsonView({FilmDTO.MinimalReq.class})
    @GetMapping("/withFilm")
    public Map<Integer, ActorFilmResponse> getAllInfo() {
        return actorFilmService.getAllActorFilmResponse();
    }
}
