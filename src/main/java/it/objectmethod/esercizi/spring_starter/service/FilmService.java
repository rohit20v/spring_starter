package it.objectmethod.esercizi.spring_starter.service;

import it.objectmethod.esercizi.spring_starter.dto.FilmActorDTO;
import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import it.objectmethod.esercizi.spring_starter.dto.FilmRecord;
import it.objectmethod.esercizi.spring_starter.dto.FilmUpdateDTO;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.mapper.FilmMapper;
import it.objectmethod.esercizi.spring_starter.mapper.FilmMapperWithMapstruct;
import it.objectmethod.esercizi.spring_starter.mapper.FilmUpdateMapping;
import it.objectmethod.esercizi.spring_starter.mapper.FilmWithActorMapping;
import it.objectmethod.esercizi.spring_starter.repository.FilmRepository;
import it.objectmethod.esercizi.spring_starter.specification.FilmSpecs;
import it.objectmethod.esercizi.spring_starter.specification.GeneralSpec;
import it.objectmethod.esercizi.spring_starter.util.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class FilmService {
    private final FilmMapper filmMapper;
    private final FilmRepository filmRepository;

    private final FilmMapperWithMapstruct filmMapperWithMapstruct;
    private final FilmWithActorMapping filmWithActorMapping;
    private final FilmUpdateMapping filmUpdateMapping;

    public FilmService(FilmMapper filmMapper, FilmRepository filmRepository, FilmMapperWithMapstruct filmMapperWithMapstruct, FilmWithActorMapping filmWithActorMapping, FilmUpdateMapping filmUpdateMapping) {
        this.filmMapper = filmMapper;
        this.filmRepository = filmRepository;
        this.filmMapperWithMapstruct = filmMapperWithMapstruct;
        this.filmWithActorMapping = filmWithActorMapping;
        this.filmUpdateMapping = filmUpdateMapping;
    }

    public FilmDTO getFilmById(Integer id) {
        return filmMapperWithMapstruct.toDTO(filmRepository.getFilmById(id));
    }

    public FilmActorDTO getFilmActorDTOById(Integer id) {
        return filmWithActorMapping.toDTO(filmRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(String.format("No film with id: %d is found", id))
        ));
    }

    public List<FilmDTO> getFilms() {
        return filmMapperWithMapstruct.toDTOs(filmRepository.findAll());
    }

    public List<FilmActorDTO> filmWithActorDetails() {
        return filmWithActorMapping.toDTOs(filmRepository.findAll());
    }

    public PaginationResponse<FilmDTO> getCustomFilmPages(final Integer page, final Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Film> filmPage = filmRepository.findAll(pageable);
        Page<FilmDTO> dtoPage = filmPage.map(filmMapperWithMapstruct::toDTO);
        return new PaginationResponse<>(dtoPage);
    }

    public Page<FilmDTO> getFilmPages(final Integer page, final Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Film> filmPage = filmRepository.findAll(pageable);
        return filmPage.map(filmMapperWithMapstruct::toDTO);
    }

    public List<FilmDTO> getFilmsUsingSpecification(String title, Date date, String category, Integer directorId) {
        return filmMapperWithMapstruct.toDTOs(filmRepository.findAll(FilmSpecs.findByAllColumns(title, date, category, directorId)));
    }

    public Page<FilmDTO> getFilmsByDirectorId(Integer id, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Film> directorPage = filmRepository.findAll(FilmSpecs.findMoviesByDirectorID(id), pageable);
        return directorPage.map(filmMapperWithMapstruct::toDTO);
    }

    public List<FilmDTO> getFilmByActorId(Integer id) {
        return filmMapperWithMapstruct.toDTOs(filmRepository.findAll(FilmSpecs.findFilmsByActorId(id)));
    }

    public List<FilmDTO> findByAllParams(Map<String, String> map) {
        return filmMapperWithMapstruct.toDTOs(filmRepository.findAll(GeneralSpec.findByAllParams(map)));
    }

    public List<FilmDTO> getFilmsUsingSpecificationDtoParams(FilmDTO filmDTO) {
        return filmMapperWithMapstruct.toDTOs(filmRepository.findAll(FilmSpecs.findByAllColumns(filmDTO)));
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

        return filmPage.map(filmMapperWithMapstruct::toDTO);
    }

//    public List<FilmDTO> getFilmsWithActorInfo() {
//        return filmMapper.mapToDtos(
//                filmRepository.findAll(
//                        FilmSpecs.findAllWithActorInfo()
//                )
//        );
//    }

    public FilmDTO save(FilmDTO dto) {
        Film entity = filmMapperWithMapstruct.toEntity(dto);
        Film save = filmRepository.save(entity);
        return filmMapperWithMapstruct.toDTO(save);
    }

    public FilmUpdateDTO save(FilmUpdateDTO dto) {
        Film entity = filmUpdateMapping.toEntity(dto);
        Film save = filmRepository.save(entity);
        return filmUpdateMapping.toDTO(save);
    }

    public FilmDTO update(FilmDTO dto) {
        if (!filmRepository.existsById(dto.getId()))
            throw new NoSuchElementException(String.format("Film with id '%d' does not exist", dto.getId()));
        return this.save(dto);
    }

    public FilmUpdateDTO update(FilmUpdateDTO dto) {
        if (!filmRepository.existsById(dto.getId()))
            throw new NoSuchElementException(String.format("Film with id '%d' does not exist", dto.getId()));
        Film entity = filmUpdateMapping.toEntity(dto);
        Film save = filmRepository.save(entity);
        return filmUpdateMapping.toDTO(save);
    }

    public void delete(Integer id) {
        if (!filmRepository.existsById(id)) throw new NoSuchElementException("The Film with id: " + id + " does not exist");
        filmRepository.deleteById(id);
    }

    public List<FilmRecord> getFilmsByGenre(String genre) {
        return filmRepository.getFilmByCategory(genre, FilmRecord.class);
    }
}
