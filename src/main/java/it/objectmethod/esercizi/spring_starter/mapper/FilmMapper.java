package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.util.BasicMethodMapping;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class FilmMapper implements BasicMethodMapping<FilmDTO, Film> {

    private final DirectorMapper directorMapper;
    private final ActorMapper actorMapper;

    public FilmMapper(DirectorMapper directorMapper, ActorMapper actorMapper) {
        this.directorMapper = directorMapper;
        this.actorMapper = actorMapper;
    }

    @Override
    public Film mapToEntity(FilmDTO filmDTO) {
        Film film = Film.builder()
                .id(filmDTO.getId())
                .title(filmDTO.getTitle())
                .date(filmDTO.getDate())
                .category(filmDTO.getCategory())
                .build();

        if (filmDTO.getDirector() != null) {
            film.setDirector(directorMapper.mapToEntity(filmDTO.getDirector()));
        }
//
//        if (filmDTO.getActors() != null && !filmDTO.getActors().isEmpty()) {
//            film.setActor(actorMapper.mapToEntities(filmDTO.getActors().stream().map(actor -> ActorDTO.builder().id(filmDTO.getId()).build()).toList()));
//        }

        return film;

    }

    @Override
    public FilmDTO mapToDto(Film film) {
        List<Integer> list = new ArrayList<>();
        if (film.getActor() != null) {
            list = film.getActor().stream()
                    .map(Actor::getId)
                    .toList();
        }

        return FilmDTO.builder()
                .id(film.getId())
                .title(film.getTitle())
                .date(film.getDate())
                .category(film.getCategory())
                .director(directorMapper.mapToDto(film.getDirector()))
                .actors(list)
                .build();
    }


//
//    @Override
//    public FilmDTO mapToDto(Film film) {
//
////        List<Integer> actorIds = new ArrayList<Integer>();
////        for (Actor actor : film.getActor()) {
////            Optional<Actor> byId = actorRepository.findById(actor.getId());
////            byId.ifPresent(a -> actorIds.add(a.getId()));
////        }
//
//        List<Integer> actorIds = film.getActor().stream()
//                .map(Actor::getId)
//                .toList();
//
//        return FilmDTO.builder()
//                .id(film.getId())
//                .title(film.getTitle())
//                .date(film.getDate())
//                .category(film.getCategory())
//                .id_director(film.getDirector() != null ? film.getDirector().getId() : null)
//                .id_actors(actorIds)
//                .build();
//    }
//
//    @Override
//    public Film mapToEntity(FilmDTO filmDTO) {
//
//        Director directorsById = directorRepository.getDirectorsById(filmDTO.getId_director());
//        List<Actor> actorList = filmDTO.getId_actors().stream()
//                .map(id -> actorRepository.findById(id).orElse(null))
//                .filter(Objects::nonNull).toList();
//

    /// /        Director directorsById = directorMapper.mapToEntity(filmDTO);
//        directorMapper.mapToEntity(directorsById)
//
//        return Film.builder()
//                .id(filmDTO.getId())
//                .title(filmDTO.getTitle())
//                .date(filmDTO.getDate())
//                .category(filmDTO.getCategory())
//                .director(directorsById)
//                .actor(actorList)
//                .build();
//    }
    @Override
    public List<Film> mapToEntities(List<FilmDTO> filmDTOS) {
        return filmDTOS.stream()
                .map(this::mapToEntity)
                .toList();
    }

    @Override
    public List<FilmDTO> mapToDtos(List<Film> films) {
        return films.stream()
                .map(this::mapToDto)
                .toList();
    }
}
