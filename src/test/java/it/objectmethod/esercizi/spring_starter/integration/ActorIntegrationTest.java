package it.objectmethod.esercizi.spring_starter.integration;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import it.objectmethod.esercizi.spring_starter.BaseIntegrationTest;
import it.objectmethod.esercizi.spring_starter.dto.ActorDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ActorIntegrationTest extends BaseIntegrationTest {
    @Test
    void shouldSaveAnActor() {
        ActorDTO expected = ActorDTO.builder()
                .id(3)
                .name("Ryan")
                .surname("Gosling")
                .dob(LocalDate.parse("1989-04-09"))
                .city("Washington")
                .films(emptyList())
                .build();

        ActorDTO given = ActorDTO.builder()
                .name("Ryan")
                .surname("Gosling")
                .dob(LocalDate.parse("1989-04-09"))
                .city("Washington")
                .films(emptyList())
                .build();


        ActorDTO actual = given()
                .port(this.port)
                .contentType(ContentType.JSON)
                .when()
                .body(given)
                .post("/api/actor/save")
                .then()
                .statusCode(200)
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

    @Test
    void shouldReturnAllActors() {
        final List<ActorDTO> expected = List.of(
                ActorDTO.builder()
                        .id(1)
                        .name("Leonardo")
                        .surname("dicaprio")
                        .dob(LocalDate.parse("1973-01-31"))
                        .city("NYC")
                        .films(List.of(
                                1
                        ))
                        .build(),
                ActorDTO.builder()
                        .id(2)
                        .name("Tom")
                        .surname("hardy")
                        .dob(LocalDate.parse("1949-02-01"))
                        .city("Japan")
                        .films(List.of(
                                2
                        ))
                        .build()
        );

        final List<ActorDTO> actual = given()
                .port(super.port)
                .when()
                .get("/api/actor/all")
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
    void shouldReturnAnActorByID() {
        final Integer id = 2;
        final ActorDTO expected = ActorDTO.builder()
                .id(2)
                .name("Tom")
                .surname("hardy")
                .dob(LocalDate.parse("1949-02-01"))
                .city("Japan")
                .films(List.of(
                        2
                ))
                .build();

        final ActorDTO actual = given()
                .port(super.port)
                .when()
                .get("/api/actor/{id}", id)
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
    void shouldReturnAnActorByParams() {
        final List<ActorDTO> expected = List.of(
                ActorDTO.builder()
                        .id(1)
                        .name("Leonardo")
                        .surname("dicaprio")
                        .dob(LocalDate.parse("1973-01-31"))
                        .city("NYC")
                        .films(List.of(
                                1
                        ))
                        .build(),
                ActorDTO.builder()
                        .id(2)
                        .name("Tom")
                        .surname("hardy")
                        .dob(LocalDate.parse("1949-02-01"))
                        .city("Japan")
                        .films(List.of(
                                2
                        ))
                        .build()
        );

        final List<ActorDTO> actual = given()
                .port(super.port)
                .param("name", "o")
                .param("surname", "a")
                .contentType(ContentType.JSON)
                .when()
                .get("api/actor/dto/by")
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
    void shouldDeleteAnActorById() {
        final Integer id = 1;

        given()
                .port(super.port)
                .delete("api/actor/delete/{id}", id)
                .then()
                .statusCode(200);

        given()
                .port(super.port)
                .get("api/actor/{id}", id)
                .then()
                .statusCode(404);
    }

    @Test
    void shouldUpdateAnActorByIdPresentInTheRequestBody() {
        final ActorDTO expected = ActorDTO.builder()
                .id(2)
                .name("Tom")
                .surname("Hardy")
                .dob(LocalDate.parse("1949-02-02"))
                .city("Japan")
                .films(List.of(
                        2
                ))
                .build();


        ActorDTO actual = given()
                .port(super.port)
                .contentType(ContentType.JSON)
                .body(expected)
                .put("/api/actor/update")
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<>() {
                });

        given()
                .port(super.port)
                .get("/api/actor/{id}", 2)
                .prettyPeek()
                .then()
                .statusCode(200);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnActorsByActorNamesIn() {
        final String[] params = {"Leonardo", "Tom"};

        final List<ActorDTO> expected = List.of(
                ActorDTO.builder()
                        .id(1)
                        .name("Leonardo")
                        .surname("dicaprio")
                        .dob(LocalDate.parse("1973-01-31"))
                        .city("NYC")
                        .films(List.of(
                                1
                        ))
                        .build(),
                ActorDTO.builder()
                        .id(2)
                        .name("Tom")
                        .surname("hardy")
                        .dob(LocalDate.parse("1949-02-01"))
                        .city("Japan")
                        .films(List.of(
                                2
                        ))
                        .build()
        );

        final List<ActorDTO> actual = given()
                .port(this.port)
                .contentType(ContentType.JSON)
                .when()
                .queryParam("names", (Object[]) params)
                .get("api/actor/names/in")
                .prettyPeek()
                .then()
                .extract()
                .as(new TypeRef<>() {
                });

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

}
