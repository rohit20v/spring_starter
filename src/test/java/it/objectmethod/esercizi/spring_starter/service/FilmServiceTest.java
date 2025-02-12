package it.objectmethod.esercizi.spring_starter.service;

import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import it.objectmethod.esercizi.spring_starter.entity.Film;
import it.objectmethod.esercizi.spring_starter.mapper.DirectorMapperWithMapstruct;
import it.objectmethod.esercizi.spring_starter.mapper.FilmMapper;
import it.objectmethod.esercizi.spring_starter.mapper.FilmMapperWithMapstruct;
import it.objectmethod.esercizi.spring_starter.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;

import java.lang.reflect.Field;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings
class FilmServiceTest {


    FilmRepository repository = mock(FilmRepository.class);
    @Mock
    FilmMapper defaultMapper;
    @Spy
    FilmMapperWithMapstruct mapper = Mappers.getMapper(FilmMapperWithMapstruct.class);
    @Spy
    DirectorMapperWithMapstruct directorMapper = Mappers.getMapper(DirectorMapperWithMapstruct.class);

    @InjectMocks
    FilmService service;


    @BeforeEach
    void setup() throws Exception {
        Field field = mapper.getClass().getDeclaredField("directorMapperWithMapstruct");
        field.setAccessible(true);
        field.set(mapper, directorMapper);
    }

    @Test
    void shouldReturnAFilm_whenInvokedGetFilmById() {
        // ARRANGE
        final Integer id = 2;
        final Film repoReturns = Film.builder()
                .id(id)
                .title("Interstellar")
                .build();

        final FilmDTO expected = FilmDTO.builder()
                .id(id)
                .title("Interstellar")
                .build();


        // ACT
        when(repository.getFilmById(id))
                .thenReturn(repoReturns);
        FilmDTO actual = service.getFilmById(id);

        // ASSERT
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    //    getFilmsByDirectorId(Integer id, Integer page, Integer size)
//    @Test
//    void shouldReturnAFilmByDirectorId_whenGetFilmsByDirectorIdIsInvoked() {
//        // ARRANGE
//        final Integer id = 3;
//        final Integer page = 0;
//        final Integer size = 2;
//
//        final List<Film> mockedData = List.of(
//                Film.builder()
//                        .id(5)
//                        .category("Drama")
//                        .build(),
//
//                Film.builder()
//                        .id(13)
//                        .category("Horror/Drama")
//                        .build()
//        );
//
//        final Page<Film> repoResult = new PageImpl<>(mockedData);
//
//
//        // ACT
//        // USES SPECIFICATIONS
//        when(repository.findAll());
//
//        // ASSERT
//
//    }


}