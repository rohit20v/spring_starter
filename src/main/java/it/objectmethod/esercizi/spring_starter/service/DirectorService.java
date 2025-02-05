package it.objectmethod.esercizi.spring_starter.service;

import it.objectmethod.esercizi.spring_starter.dto.DirectorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Director;
import it.objectmethod.esercizi.spring_starter.mapper.DirectorMapper;
import it.objectmethod.esercizi.spring_starter.repository.DirectorRepository;
import it.objectmethod.esercizi.spring_starter.specification.DirectorSpecs;
import it.objectmethod.esercizi.spring_starter.specification.GeneralSpec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;

    public DirectorService(DirectorRepository directorRepository, DirectorMapper directorMapper) {
        this.directorRepository = directorRepository;
        this.directorMapper = directorMapper;
    }

    public DirectorDTO getDirectorById(Integer id) {
        return directorMapper.mapToDto(directorRepository.getDirectorsById(id));
    }

    public List<DirectorDTO> getAllDirectors() {
        return directorMapper.mapToDtos(directorRepository.findAll());
    }

    public List<DirectorDTO> getAllDirectorsBySpecification(String name, String surname, String city) {
        return directorMapper.mapToDtos(
                directorRepository.findAll(
                        DirectorSpecs.findByAllColumns(name, surname, city)
                )
        );
    }

    public List<DirectorDTO> getAllDirectorsBySpecification(DirectorDTO dto) {
        return directorMapper.mapToDtos(
                directorRepository.findAll(
                        DirectorSpecs.findByAllColumns(dto)
                )
        );
    }


    public void deleteDirectorById(Integer id) {
        directorRepository.deleteById(id);
    }

    public DirectorDTO save(DirectorDTO dto) {
        Director entity = directorMapper.mapToEntity(dto);
        directorRepository.save(entity);
        return directorMapper.mapToDto(entity);
    }


    public List<DirectorDTO> findAllDirectorsFilteredUsingSpecs(DirectorDTO dto) {
        Specification<Director> allDirectorsWithFilteredName = DirectorRepository.Specs.findAllDirectorsWithFilteredName(dto.getName());
        Specification<Director> allDirectorsWithFilteredSurname = DirectorRepository.Specs.findAllDirectorsWithFilteredSurname(dto.getSurname());

        return directorMapper.mapToDtos(
                directorRepository.findAll(
                        Specification.where(allDirectorsWithFilteredName).or(allDirectorsWithFilteredSurname)
                )
        );
    }

    public List<DirectorDTO> findByAllParams(Map<String, String> map) {
        return directorMapper.mapToDtos(
                directorRepository.findAll(
                        GeneralSpec.<Director>findByAllParams(map)
                )
        );
    }

    public DirectorDTO update(DirectorDTO dto) {
        return this.save(dto);
    }
}
