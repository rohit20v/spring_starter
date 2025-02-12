package it.objectmethod.esercizi.spring_starter.service;

import it.objectmethod.esercizi.spring_starter.dto.DirectorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Director;
import it.objectmethod.esercizi.spring_starter.mapper.DirectorMapper;
import it.objectmethod.esercizi.spring_starter.mapper.DirectorMapperWithMapstruct;
import it.objectmethod.esercizi.spring_starter.repository.DirectorRepository;
import it.objectmethod.esercizi.spring_starter.util.PaginationResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class DirectorServiceTest {

    @InjectMocks
    DirectorService directorService;
    @Mock
    private DirectorRepository directorRepository;
    @Mock
    private DirectorMapper mapper;
    @Spy
    private DirectorMapperWithMapstruct directorMapperWithMapstruct = Mappers.getMapper(DirectorMapperWithMapstruct.class);

    @Test
    void shouldReturnDirectorById_whenInvokedGetDirectorById() {
        final Integer id = 1;
        // ARRANGE
        DirectorDTO expected = DirectorDTO.builder()
                .id(id)
                .name("Chris")
                .surname("Nolan")
                .city("NYC")
                .build();

        Director entity = Director.builder()
                .id(id)
                .name("Chris")
                .surname("Nolan")
                .city("NYC")
                .build();

        // ACT
        when(directorRepository.getDirectorsById(id))
                .thenReturn(entity);
        DirectorDTO actual = directorService.getDirectorById(id);

        // ASSERT
        assertThat(actual)
//                .as(actual.getId() + " is equals to: " + expected.getId())
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }

    @Test
    void shouldReturnDirectorList_whenGetAllDirectorsIsInvoked() {
        final Integer id = 1;
        final Integer id_2 = 2;

        // ARRAGE
        List<DirectorDTO> expected = List.of(
                DirectorDTO.builder()
                        .id(id)
                        .name("Chris")
                        .surname("Nolan")
                        .city("NYC")
                        .build(),
                DirectorDTO.builder()
                        .id(id_2)
                        .name("Ryan")
                        .surname("Reynolds")
                        .city("Ohio")
                        .build()
        );

        List<Director> entityList = List.of(
                Director.builder()
                        .id(id)
                        .name("Chris")
                        .surname("Nolan")
                        .city("NYC")
                        .build(),
                Director.builder()
                        .id(id_2)
                        .name("Ryan")
                        .surname("Reynolds")
                        .city("Ohio")
                        .build()
        );


        // ACT
        when(directorRepository.findAll())
                .thenReturn(entityList);
        List<DirectorDTO> actual = directorService.getAllDirectors();

        // ASSERT
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldGetDirectorPage_whenInvoked() {
        final Integer id = 1;
        final Integer id_2 = 1;

        // ARRANGE
        Page<Director> mockData = new PageImpl<>(
                List.of(
                        Director.builder()
                                .id(id)
                                .name("Chris")
                                .surname("Nolan")
                                .city("NYC")
                                .build(),
                        Director.builder()
                                .id(id_2)
                                .name("Ryan")
                                .surname("Reynolds")
                                .city("Ohio")
                                .build()
                )
        );
        Page<DirectorDTO> pageDirectorDto = mockData.map(directorMapperWithMapstruct::toDTO);
        PaginationResponse<DirectorDTO> expected = new PaginationResponse<>(pageDirectorDto);

        // ACT
        when(directorRepository.findAll(PageRequest.of(0, 2)))
                .thenReturn(mockData);
        PaginationResponse<DirectorDTO> actual = directorService.getDirectorPages(PageRequest.of(0, 2));

        // ASSERT
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);


    }

    @Test
    void shouldDelete_whenDeleteDirectorByIdIsInvoked() {
        // ARRANGE
        final Integer id = 1;

        // ACT -> while working with void methods asserts and actions replace their position, so basically first we call the service and then the repository
        directorService.deleteDirectorById(id);

        // ASSERT
        verify(directorRepository).deleteById(id);
    }

    @Test
    void shouldSaveDirector_whenSaveIsInvoked() {
        // ARRANGE
        Director entity = Director.builder()
                .name("Tom")
                .surname("hanks")
                .city("NYC")
                .build();

        Director res = Director.builder()
                .id(1)
                .name("Tom")
                .surname("hanks")
                .city("NYC")
                .build();

//        DirectorDTO expected = directorMapperWithMapstruct.toDTO(res);
//
        DirectorDTO dto = directorMapperWithMapstruct.toDTO(entity);

        // ACT
        when(directorRepository.save(entity))
                .thenReturn(res);
        DirectorDTO actual = directorService.save(dto);

        // ASSERT
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(dto);

    }

}