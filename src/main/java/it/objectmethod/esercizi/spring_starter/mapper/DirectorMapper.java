package it.objectmethod.esercizi.spring_starter.mapper;

import it.objectmethod.esercizi.spring_starter.dto.DirectorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Director;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.util.BasicMethodMapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DirectorMapper implements BasicMethodMapping<DirectorDTO, Director> {

    @Override
    public DirectorDTO mapToDto(Director director) {
        List<Integer> films;
        if (director != null && director.getFilms() != null) {
            films = director.getFilms().stream()
                    .map(Film::getId)
                    .toList();
            return DirectorDTO.builder()
                    .id(director.getId())
                    .name(director.getName())
                    .surname(director.getSurname())
                    .city(director.getCity())
                    .films(films)
                    .build();
        }
        return null;
    }

    @Override
    public Director mapToEntity(DirectorDTO directorDTO) {

        List<Film> list = new ArrayList<Film>();
        if (directorDTO.getFilms() != null) {
            list = directorDTO.getFilms().stream()
                    .map(v -> Film.builder().id(v).build())
                    .toList();
        }
        return Director.builder()
                .id(directorDTO.getId())
                .name(directorDTO.getName())
                .surname(directorDTO.getSurname())
                .city(directorDTO.getCity())
                .build();
    }

    @Override
    public List<Director> mapToEntities(List<DirectorDTO> directorDTOS) {
        return directorDTOS.stream()
                .map(this::mapToEntity)
                .toList();
    }

    @Override
    public List<DirectorDTO> mapToDtos(List<Director> directors) {
        return directors.stream()
                .map(this::mapToDto)
                .toList();
    }
}