package it.objectmethod.esercizi.spring_starter.integration;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import it.objectmethod.esercizi.spring_starter.BaseIntegrationTest;
import it.objectmethod.esercizi.spring_starter.dto.DirectorDTO;
import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class FilmIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldReturnAFilmById() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Integer id = 1;
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
                .then()
                .extract()
                .as(new TypeRef<FilmDTO>() {
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

}
