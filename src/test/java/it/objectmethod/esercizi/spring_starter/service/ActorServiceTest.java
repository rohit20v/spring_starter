package it.objectmethod.esercizi.spring_starter.service;

import it.objectmethod.esercizi.spring_starter.dao.ActorSearchDAO;
import it.objectmethod.esercizi.spring_starter.dto.ActorDTO;
import it.objectmethod.esercizi.spring_starter.entity.Actor;
import it.objectmethod.esercizi.spring_starter.mapper.ActorCompleteMapstruct;
import it.objectmethod.esercizi.spring_starter.mapper.ActorMapper;
import it.objectmethod.esercizi.spring_starter.mapper.ActorMapperWIthMapstruct;
import it.objectmethod.esercizi.spring_starter.repository.ActorRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@MockitoSettings
class ActorServiceTest {

    @Mock
    private ActorSearchDAO actorSearchDAO;
    @Mock
    private ActorMapper actorMapper;
    @Mock
    private ActorRepository actorRepository;
    @Spy
    private ActorMapperWIthMapstruct actorMapperWIthMapstruct = Mappers.getMapper(ActorMapperWIthMapstruct.class);
    @Spy
    private ActorCompleteMapstruct actorCompleteMapstruct = Mappers.getMapper(ActorCompleteMapstruct.class);
    @InjectMocks
    private ActorService actorService;

    @Test
    void shouldReturnActorDto_whenInvokeFindById() {
        //ARRANGE
        final Integer id = 1;
        final ActorDTO expected = ActorDTO.builder()
                .id(id)
                .name("name")
                .surname("surname")
                .build();
        final Actor entity = Actor.builder()
                .id(id)
                .name("name")
                .surname("surname")
                .build();

        //ACT
        when(actorRepository.findById(id))
                .thenReturn(Optional.ofNullable(entity));
        final ActorDTO actual = actorService.findById(id);

        //ASSERT
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnActorPage_whenInvokedGetPage() {
        //Arrange
        final Integer id = 1;
        final Integer id_2 = 2;
        final Integer page = 0;
        final Integer size = 4;

        final List<Actor> mockData = List.of(
                Actor.builder()
                        .id(id)
                        .name("name")
                        .surname("surname")
                        .build(),
                Actor.builder()
                        .id(id_2)
                        .name("second name")
                        .surname("second surname")
                        .build()
        );

        final Page<Actor> actorPage = new PageImpl<>(mockData);

        Page<ActorDTO> defaultDtoPage = actorPage.map(actorMapperWIthMapstruct::toDTO);
        PaginationResponse<ActorDTO> expected = new PaginationResponse<>(defaultDtoPage);

        //ACTION
        when(actorRepository.findAll(PageRequest.of(page, size)))
                .thenReturn(actorPage);
        final PaginationResponse<ActorDTO> actual = actorService.getPage(page, size, "");

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

}