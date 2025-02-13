package it.objectmethod.esercizi.spring_starter.integration;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import it.objectmethod.esercizi.spring_starter.BaseIntegrationTest;
import it.objectmethod.esercizi.spring_starter.dto.DirectorDTO;
import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import it.objectmethod.esercizi.spring_starter.util.PaginationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class FilmIntegrationTest extends BaseIntegrationTest {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final List<FilmDTO> expected = List.of(
            FilmDTO.builder()
                    .id(1)
                    .title("Inception")
                    .date(simpleDateFormat.parse("2010-09-24"))
                    .category("Sci-fi/Action")
                    .director(
                            DirectorDTO.builder()
                                    .id(1)
                                    .name("Christopher")
                                    .surname("Nolan")
                                    .city("UK")
                                    .films(List.of(
                                            1, 2
                                    ))
                                    .build())
                    .actors(List.of(
                            1
                    ))
                    .build(),
            FilmDTO.builder()
                    .id(2)
                    .title("Interstellar")
                    .date(simpleDateFormat.parse("2014-04-20"))
                    .category("Sci-fi/Action")
                    .director(
                            DirectorDTO.builder()
                                    .id(1)
                                    .name("Christopher")
                                    .surname("Nolan")
                                    .city("UK")
                                    .films(List.of(
                                            1, 2
                                    ))
                                    .build())
                    .actors(List.of(
                            2
                    ))
                    .build(),
            FilmDTO.builder()
                    .id(3)
                    .title("Shrek")
                    .date(simpleDateFormat.parse("2005-04-20"))
                    .category("Kid")
                    .director(null)
                    .actors(emptyList())
                    .build()
    );

    public FilmIntegrationTest() throws ParseException {
    }

    @Test
    void shouldReturnAllFilms()  {

        final List<FilmDTO> actual = given()
                .port(super.port)
                .when()
                .contentType(ContentType.JSON)
                .get("/api/film/all")
                .prettyPeek()
                .then()
                .extract()
                .as(new TypeRef<>() {
                });

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }

    @Test
    void shouldReturnAFilmById() throws ParseException {
        final Integer id = 1;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse("2010-09-24");
        final FilmDTO expected = FilmDTO.builder()
                .id(id)
                .title("Inception")
                .date(parse)
                .category("Sci-fi/Action")
                .director(
                        DirectorDTO.builder()
                                .id(id)
                                .name("Christopher")
                                .surname("Nolan")
                                .city("UK")
                                .films(List.of(
                                        1, 2
                                ))
                                .build())
                .actors(List.of(
                        1
                ))
                .build();

        final FilmDTO actual = given()
                .port(super.port)
                .when()
                .contentType(ContentType.JSON)
                .get("/api/film/{id}", id)
                .prettyPeek()
                .then()
                .extract()
                .as(new TypeRef<>() {
                });

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }

    @Test
    void shouldDeleteById() {
        final Integer id = 3;

        given()
                .port(this.port)
                .delete("/api/film/delete/{id}", id)
                .then()
                .statusCode(200);

        given()
                .port(this.port)
                .get("api/film/{id}", id)
                .then()
                .statusCode(404);

    }

    @Test
    void shouldReturnAllFilmPagesWithRequestParams() {
        final Integer page = 0;
        final Integer size = 3;

        Pageable pageable = PageRequest.of(page, size);
        final Page<FilmDTO> expectedPage = new PageImpl<>(
                this.expected,
                pageable,
                this.expected.size()
        );

        PaginationResponse<FilmDTO> expected = new PaginationResponse<>(expectedPage);

        final PaginationResponse<FilmDTO> actual = given()
                .port(this.port)
                .contentType(ContentType.JSON)
                .when()
                .queryParam("page", page)
                .queryParam("size", size)
                .get("api/film/custompage")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<>() {
                });

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnAllFilmPagesWithDefaultParams() {
        final Integer page = 0;
        final Integer size = 2;

        Pageable pageable = PageRequest.of(page, size);
        final Page<FilmDTO> expectedPage = new PageImpl<>(
                this.expected.subList(0, expected.size() - 1),
                pageable,
                expected.size()
        );

        PaginationResponse<FilmDTO> expected = new PaginationResponse<>(expectedPage);

        final PaginationResponse<FilmDTO> actual = given()
                .port(this.port)
                .contentType(ContentType.JSON)
                .when()
                .get("api/film/custompage")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<>() {
                });

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

}
