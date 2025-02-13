package it.objectmethod.esercizi.spring_starter.integration;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import it.objectmethod.esercizi.spring_starter.BaseIntegrationTest;
import it.objectmethod.esercizi.spring_starter.dto.DirectorDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DirectorIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldReturnAllDirectors() {
        final List<DirectorDTO> expected = List.of(
                DirectorDTO.builder()
                        .id(1)
                        .name("Christopher")
                        .surname("Nolan")
                        .city("UK")
                        .films(List.of(
                                1, 2
                        ))
                        .build(),
                DirectorDTO.builder()
                        .id(2)
                        .name("Mary")
                        .surname("Harron")
                        .city("USA")
                        .films(emptyList())
                        .build()
        );
        final List<DirectorDTO> actual = given()
                .port(this.port)
                .when()
                .get("/api/director/all")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
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
                .delete("api/director/delete/{id}", id)
                .then()
                .statusCode(200);

        given()
                .port(this.port)
                .get("api/director/{id}", id)
                .then()
                .statusCode(404);

    }

    @Test
    void shouldSaveADirector() {
        DirectorDTO expected = DirectorDTO.builder()
                .id(4)
                .name("Ryan")
                .surname("Gosling")
                .city("Washington")
                .films(emptyList())
                .build();

        DirectorDTO given = DirectorDTO.builder()
                .name("Ryan")
                .surname("Gosling")
                .city("Washington")
                .films(emptyList())
                .build();


        DirectorDTO actual = given()
                .port(this.port)
                .contentType(ContentType.JSON)
                .when()
                .body(given)
                .post("/api/director/save")
                .then()
                .statusCode(201)
                .extract()
                .as(new TypeRef<>() {
                });

//        given()
//                .port(super.port)
//                .get("/api/actor/{id}", 3)
//                .then()
//                .statusCode(200);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
