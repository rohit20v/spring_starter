package it.objectmethod.esercizi.spring_starter.service;

import com.fasterxml.jackson.annotation.JsonView;
import it.objectmethod.esercizi.spring_starter.dao.ActorSearchDAO;
import it.objectmethod.esercizi.spring_starter.dto.ActorCompleteDTO;
import it.objectmethod.esercizi.spring_starter.dto.ActorDTO;
import it.objectmethod.esercizi.spring_starter.dto.ActorRecord;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.mapper.ActorCompleteMapstruct;
import it.objectmethod.esercizi.spring_starter.mapper.ActorMapper;
import it.objectmethod.esercizi.spring_starter.mapper.ActorMapperWIthMapstruct;
import it.objectmethod.esercizi.spring_starter.repository.ActorRepository;
import it.objectmethod.esercizi.spring_starter.specification.ActorSpecs;
import it.objectmethod.esercizi.spring_starter.specification.GeneralSpec;
import it.objectmethod.esercizi.spring_starter.util.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static it.objectmethod.esercizi.spring_starter.controller.controllerAdvice.BasicResponseException.badRequestException;
import static it.objectmethod.esercizi.spring_starter.controller.controllerAdvice.BasicResponseException.notFoundException;
import static it.objectmethod.esercizi.spring_starter.dto.FilmDTO.MinimalReq;

@Service
public class ActorService {
    private final ActorSearchDAO actorDao;
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    private final ActorMapperWIthMapstruct actorMapperWIthMapstruct;
    private final ActorCompleteMapstruct actorCompleteMapstruct;

    public ActorService(ActorSearchDAO actorDao, final ActorRepository actorRepository, final ActorMapper actorMapper, final ActorMapperWIthMapstruct actorMapperWIthMapstruct, ActorCompleteMapstruct actorCompleteMapstruct) {
        this.actorDao = actorDao;
        this.actorRepository = actorRepository;
        this.actorMapper = actorMapper;

        this.actorMapperWIthMapstruct = actorMapperWIthMapstruct;
        this.actorCompleteMapstruct = actorCompleteMapstruct;
    }

    public ActorDTO findById(final Integer id) {
        return actorMapperWIthMapstruct.toDTO(actorRepository.findById(id)
                .orElseThrow(
                        () -> notFoundException("Could not find actor on %s with id %d", Actor.class.getSimpleName(), id)));
    }

    public List<ActorDTO> getAll() {
        return actorMapperWIthMapstruct.toDTOs(actorRepository.findAll());
    }

    public PaginationResponse<ActorDTO> getPage(final Integer page, Integer size, String size_) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Actor> actorPage = actorRepository.findAll(pageable);
//
//        if (size_ != null && (size_.equalsIgnoreCase("max") || size_.equalsIgnoreCase("all"))) {
//            size = (int) actorPage.getTotalElements();
//        }

        pageable = PageRequest.of(page, size);
        actorPage = actorRepository.findAll(pageable);

        Page<ActorDTO> actorDtoPage = actorPage.map(actorMapperWIthMapstruct::toDTO);
        return new PaginationResponse<>(actorDtoPage);
    }

    public List<ActorDTO> findAllByCriteria(String name, String surname, String city) {
        return actorDao.finalAll(name, surname, city);
    }

    public List<ActorDTO> findAllBySpecs(String name, String surname, LocalDate dob, String city) {
        List<ActorDTO> dtOs = actorMapperWIthMapstruct.toDTOs(
                actorRepository.findAll(
                        ActorSpecs.findByAllColumns(
                                name,
                                surname,
                                dob,
                                city
                        )
                )
        );
        dtOs.forEach(System.out::println);
        return dtOs;
    }

    public List<ActorDTO> findAllBySpecs(ActorDTO actorDTO) {
        List<Actor> all = actorRepository.findAll(ActorSpecs.findByAllColumns(actorDTO));
        all.forEach(System.out::println);
        return actorMapperWIthMapstruct.toDTOs(
                all
        );
    }

    public ActorDTO findByName(final String name) {
        return actorMapperWIthMapstruct.toDTO(actorRepository.findByName(name).orElseThrow());
    }

    public ActorDTO save(final ActorDTO actorDTO) {
        Actor entity = actorMapperWIthMapstruct.toEntity(actorDTO);
        actorRepository.save(entity);
        return actorMapperWIthMapstruct.toDTO(entity);
    }

    public ActorDTO update(ActorDTO dto) {
        boolean exists = actorRepository.existsById(actorMapperWIthMapstruct.toEntity(dto).getId());
        if (exists) {
            return this.save(dto);
        }
        return null;
    }

    public List<ActorDTO> getActorsFilteredByCityUsingJPQL(String city) {
        Optional<List<Actor>> actorsByCityJPQL = actorRepository.getActorsByCityJPQL(city);
        List<ActorDTO> actors = new ArrayList<>();
        actorsByCityJPQL.ifPresent(actorList -> actors.addAll(actorMapperWIthMapstruct.toDTOs(actorList)));
        return actors;
    }

    public List<ActorDTO> getActorsFilteredByCityUsingNative(String city) {
        Optional<List<Actor>> actorsByCityJPQL = actorRepository.getActorsByCityNative(city);
        List<ActorDTO> actors = new ArrayList<>();
        actorsByCityJPQL.ifPresent(actorList -> actors.addAll(actorMapperWIthMapstruct.toDTOs(actorList)));
        return actors;
    }

    public List<ActorDTO> getFilmsByActorId(Integer id) {
        return actorMapperWIthMapstruct.toDTOs(
                actorRepository.findAll(
                        ActorSpecs.findActorsFilmsById(id)
                )
        );
    }

    public List<ActorDTO> findByAllParams(Map<String, String> map) {
        List<ActorDTO> dtOs;
        try {
            dtOs = actorMapperWIthMapstruct.toDTOs(
                    actorRepository.findAll(
                            GeneralSpec.findByAllParams(map)
                    )
            );
        } catch (Exception e) {
            throw badRequestException("Parameter not found");
        }
        return dtOs;
    }

    @JsonView({MinimalReq.class})
    public List<ActorCompleteDTO> getEverything() {
        return actorCompleteMapstruct.mapToDtos(actorRepository.findAll());
    }

    public void deleteActorById(Integer id) {
        actorRepository.deleteById(id);
    }

    public List<ActorDTO> findByNameIn(String[] names) {
        return actorMapperWIthMapstruct.toDTOs(
                actorRepository.findAll(
                        ActorSpecs.findByActorNameIn(names)
                )
        );
    }

    public ActorRecord getActorRecord(String city) {
        return actorRepository.findByCity(city, ActorRecord.class).orElseThrow(() -> notFoundException("Could not find actor in %s", Actor.class.getSimpleName(), city));
    }
}