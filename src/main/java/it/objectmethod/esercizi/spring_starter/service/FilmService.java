package it.objectmethod.esercizi.spring_starter.service;

import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.mapper.FilmMapper;
import it.objectmethod.esercizi.spring_starter.repository.FilmRepository;
import it.objectmethod.esercizi.spring_starter.specification.FilmSpecs;
import it.objectmethod.esercizi.spring_starter.specification.GeneralSpec;
import it.objectmethod.esercizi.spring_starter.util.PaginationResponse;
import jakarta.persistence.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FilmService {
    private final FilmMapper filmMapper;
    private final FilmRepository filmRepository;

    public FilmService(FilmMapper filmMapper, FilmRepository filmRepository) {
        this.filmMapper = filmMapper;
        this.filmRepository = filmRepository;
    }

    public FilmDTO getFilmById(Integer id) {
        return filmMapper.mapToDto(filmRepository.getFilmById(id));
    }

    public List<FilmDTO> getFilms() {
        return filmMapper.mapToDtos(filmRepository.findAll());
    }

    public PaginationResponse<FilmDTO> getCustomFilmPages(final Integer page, final Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Film> filmPage = filmRepository.findAll(pageable);
        Page<FilmDTO> dtoPage = filmPage.map(filmMapper::mapToDto);
        return new PaginationResponse<>(dtoPage);
    }

    public Page<FilmDTO> getFilmPages(final Integer page, final Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Film> filmPage = filmRepository.findAll(pageable);
        return filmPage.map(filmMapper::mapToDto);
    }

    public List<FilmDTO> getFilmsUsingSpecification(String title, Date date, String category) {
        return filmMapper.mapToDtos(
                filmRepository.findAll(
                        FilmSpecs.findByAllColumns(title, date, category)
                )
        );
    }

    public Page<FilmDTO> getFilmsByDirectorId(Integer id, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Film> directorPage = filmRepository.findAll(FilmSpecs.findMoviesByDirectorID(id), pageable);
        return directorPage.map(filmMapper::mapToDto);
    }

    public List<FilmDTO> getFilmByActorId(Integer id) {
        return filmMapper.mapToDtos(
                filmRepository.findAll(
                        FilmSpecs.findFilmsByActorId(id)
                )
        );
    }

    public List<FilmDTO> findByAllParams(Map<String, String> map) {
        return filmMapper.mapToDtos(
                filmRepository.findAll(
                        GeneralSpec.<Film>findByAllParams(map)
                )
        );
    }

    public List<FilmDTO> getFilmsUsingSpecification(FilmDTO filmDTO) {
        return filmMapper.mapToDtos(
                filmRepository.findAll(
                        FilmSpecs.findByAllColumns(filmDTO)
                )
        );
    }

    public Page<FilmDTO> getFilmPagesUsingSpecification(
            FilmDTO filmDTO,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Film> filmPage = filmRepository.findAll(
                FilmSpecs.findByAllColumns(filmDTO),
                pageable
        );

        return filmPage.map(filmMapper::mapToDto);
    }

//    public List<FilmDTO> getFilmsWithActorInfo() {
//        return filmMapper.mapToDtos(
//                filmRepository.findAll(
//                        FilmSpecs.findAllWithActorInfo()
//                )
//        );
//    }

    public FilmDTO save(FilmDTO dto) {
        Film entity = filmMapper.mapToEntity(dto);
        filmRepository.save(entity);
        return filmMapper.mapToDto(entity);
    }

    @Transient
    public FilmDTO update(FilmDTO dto) {
        return this.save(dto);
    }

    public void delete(Integer id) {
        filmRepository.deleteById(id);
    }
}
