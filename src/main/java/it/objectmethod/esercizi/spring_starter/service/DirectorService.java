package it.objectmethod.esercizi.spring_starter.service;

import it.objectmethod.esercizi.spring_starter.dto.DirectorDTO;
import it.objectmethod.esercizi.spring_starter.dto.IdNameDTO;
import it.objectmethod.esercizi.spring_starter.entity.Director;
import it.objectmethod.esercizi.spring_starter.mapper.DirectorMapper;
import it.objectmethod.esercizi.spring_starter.mapper.DirectorMapperWithMapstruct;
import it.objectmethod.esercizi.spring_starter.repository.DirectorRepository;
import it.objectmethod.esercizi.spring_starter.specification.DirectorSpecs;
import it.objectmethod.esercizi.spring_starter.specification.GeneralSpec;
import it.objectmethod.esercizi.spring_starter.util.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;

    private final DirectorMapperWithMapstruct directorMapperWithMapstruct;

    public DirectorService(DirectorRepository directorRepository, DirectorMapper directorMapper, DirectorMapperWithMapstruct directorMapperWithMapstruct) {
        this.directorRepository = directorRepository;
        this.directorMapper = directorMapper;

        this.directorMapperWithMapstruct = directorMapperWithMapstruct;
    }

    public DirectorDTO getDirectorById(Integer id) {
        return directorMapperWithMapstruct.toDTO(directorRepository.getDirectorsById(id));
    }

    public List<DirectorDTO> getAllDirectors() {
        return directorMapperWithMapstruct.toDTOs(directorRepository.findAll());
    }

    public List<IdNameDTO> getAllDirectorsIdAndName() {
        return directorRepository.getMeAll(IdNameDTO.class);
    }

    public PaginationResponse<DirectorDTO> getDirectorPages(Pageable pageable) {
        Page<Director> directors = directorRepository.findAll(pageable);
        Page<DirectorDTO> directorDTOS = directors.map(directorMapperWithMapstruct::toDTO);

        return new PaginationResponse<>(directorDTOS);
    }

    public List<DirectorDTO> getAllDirectorsBySpecification(String name, String surname, String city) {
        return directorMapperWithMapstruct.toDTOs(
                directorRepository.findAll(
                        DirectorSpecs.findByAllColumns(name, surname, city)
                )
        );
    }

    public List<DirectorDTO> getAllDirectorsBySpecification(DirectorDTO dto) {
        return directorMapperWithMapstruct.toDTOs(
                directorRepository.findAll(
                        DirectorSpecs.findByAllColumns(dto)
                )
        );
    }

    public void deleteDirectorById(Integer id) {
        directorRepository.deleteById(id);
    }

    public DirectorDTO save(DirectorDTO dto) {
        Director entity = directorMapperWithMapstruct.toEntity(dto);
        directorRepository.save(entity);
        return directorMapperWithMapstruct.toDTO(entity);
    }

    public List<DirectorDTO> findAllDirectorsFilteredUsingSpecs(DirectorDTO dto) {
        Specification<Director> allDirectorsWithFilteredName = DirectorRepository.Specs.findAllDirectorsWithFilteredName(dto.getName());
        Specification<Director> allDirectorsWithFilteredSurname = DirectorRepository.Specs.findAllDirectorsWithFilteredSurname(dto.getSurname());

        return directorMapperWithMapstruct.toDTOs(
                directorRepository.findAll(
                        Specification.where(allDirectorsWithFilteredName).or(allDirectorsWithFilteredSurname)
                )
        );
    }

    public List<DirectorDTO> findByAllParams(Map<String, String> map) {
        return directorMapperWithMapstruct.toDTOs(
                directorRepository.findAll(
                        GeneralSpec.findByAllParams(map)
                )
        );
    }

    public DirectorDTO update(DirectorDTO dto) {
        if (!directorRepository.existsById(dto.getId()))
            throw new NoSuchElementException(String.format("Director with id '%d' does not exist", dto.getId()));
        return this.save(dto);
    }
}
