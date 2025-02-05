package it.objectmethod.esercizi.spring_starter.service;

import it.objectmethod.esercizi.spring_starter.dao.ActorSearchDAO;
import it.objectmethod.esercizi.spring_starter.dto.ActorDTO;
import it.objectmethod.esercizi.spring_starter.dto.ActorFilmDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.mapper.ActorMapper;
import it.objectmethod.esercizi.spring_starter.repository.ActorRepository;
import it.objectmethod.esercizi.spring_starter.specification.ActorSpecs;
import it.objectmethod.esercizi.spring_starter.specification.GeneralSpec;
import it.objectmethod.esercizi.spring_starter.util.PaginationResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActorService {
    private final ActorSearchDAO actorDao;
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public ActorService(ActorSearchDAO actorDao, final ActorRepository actorRepository, final ActorMapper actorMapper, RepositoryMethodInvocationListener repositoryMethodInvocationListener) {
        this.actorDao = actorDao;
        this.actorRepository = actorRepository;
        this.actorMapper = actorMapper;
    }

    public ActorDTO findById(final Integer id) {
        return actorMapper.mapToDto(actorRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String
                                .format("Could not find actor with id %d", id))));
    }

    public List<ActorDTO> getAll() {
        return actorMapper.mapToDtos(actorRepository.findAll());
    }

    public PaginationResponse<ActorDTO> getPage(final Integer page, final Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Actor> actorPage = actorRepository.findAll(pageable);
        Page<ActorDTO> actorDtoPage = actorPage.map(actorMapper::mapToDto);
        return new PaginationResponse<>(actorDtoPage);
    }

    public List<ActorDTO> findAllByCriteria(String name, String surname, String city) {
        return actorDao.finalAll(name, surname, city);
    }

    public List<ActorDTO> findAllBySpecs(String name, String surname, Date dob, String city) {
        return actorMapper.mapToDtos(
                actorRepository.findAll(
                        ActorSpecs.findByAllColumns(
                                name,
                                surname,
                                dob,
                                city
                        )
                )
        );
    }

    public List<ActorDTO> findAllBySpecs(ActorDTO actorDTO) {
        return actorMapper.mapToDtos(
                actorRepository.findAll(ActorSpecs.findByAllColumns(actorDTO))
        );
    }

    public ActorDTO findByName(final String name) {
        return actorMapper.mapToDto(actorRepository.findByName(name).orElseThrow());
    }


    public ActorDTO save(final ActorDTO actorDTO) {
        Actor entity = actorMapper.mapToEntity(actorDTO);
        actorRepository.save(entity);
        return actorMapper.mapToDto(entity);
    }

    public ActorDTO update(ActorDTO dto) {
        boolean exists = actorRepository.existsById(actorMapper.mapToEntity(dto).getId());
        if (exists) {
            return this.save(dto);
        }
        return null;
    }

    public List<ActorDTO> getActorsFilteredByCityUsingJPQL(String city) {
        Optional<List<Actor>> actorsByCityJPQL = actorRepository.getActorsByCityJPQL(city);
        List<ActorDTO> actors = new ArrayList<>();
        actorsByCityJPQL.ifPresent(actorList -> actors.addAll(actorMapper.mapToDtos(actorList)));
        return actors;
    }

    public List<ActorDTO> getActorsFilteredByCityUsingNative(String city) {
        Optional<List<Actor>> actorsByCityJPQL = actorRepository.getActorsByCityNative(city);
        List<ActorDTO> actors = new ArrayList<>();
        actorsByCityJPQL.ifPresent(actorList -> actors.addAll(actorMapper.mapToDtos(actorList)));
        return actors;
    }

    public List<ActorDTO> getFilmsByActorId(Integer id) {
        return actorMapper.mapToDtos(
                actorRepository.findAll(
                        ActorSpecs.findActorsFilmsById(id)
                )
        );
    }

    public List<ActorDTO> findByAllParams(Map<String, String> map) {
        return actorMapper.mapToDtos(
                actorRepository.findAll(
                        GeneralSpec.<Actor>findByAllParams(map)
                )
        );
    }

    public ActorFilmDTO getEverything() {
        return actorRepository.getAllFilmActorInfo().get();
    }

    public void deleteActorById(Integer id) {
        actorRepository.deleteById(id);
    }


}
