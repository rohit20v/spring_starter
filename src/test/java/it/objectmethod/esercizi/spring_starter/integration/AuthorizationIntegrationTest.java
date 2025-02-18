package it.objectmethod.esercizi.spring_starter.integration;


import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import it.objectmethod.esercizi.spring_starter.BaseIntegrationTest;
import it.objectmethod.esercizi.spring_starter.dto.auth.AuthenticationResponseDTO;
import it.objectmethod.esercizi.spring_starter.dto.auth.AuthorizationRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AuthorizationIntegrationTest extends BaseIntegrationTest {
    private String jwtToken;

    @BeforeEach
    void setup() {
        AuthorizationRequestDTO given = AuthorizationRequestDTO.builder()
                .name("rohit")
                .email("boom.bam@gmail.co")
                .build();

        AuthenticationResponseDTO response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .body(given)
                .post("/api/auth/login")
                .then()
                .extract()
                .as(AuthenticationResponseDTO.class);

        this.jwtToken = response.token();
    }

    @Test
    @Order(0)
    void getAllUsers() {
        List<AuthorizationRequestDTO> expected = List.of(
                AuthorizationRequestDTO.builder()
                        .id(1)
                        .name("rohit")
                        .email("boom.bam@gmail.co")
                        .build(),
                AuthorizationRequestDTO.builder()
                        .id(2)
                        .name("rohit")
                        .email("r.v@gmail.com")
                        .build()
        );

        List<AuthorizationRequestDTO> actual = given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/auth/allUsers")
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
    @Order(1)
    void shouldReturnToken() {
        assertThat(jwtToken).isNotNull().isNotEmpty();
    }


    @Test
    @Order(2)
    void shouldSignUpUser() {
        final Map<String, String> expected = Map.of("message", "User: bro has been registered successfully");

        AuthorizationRequestDTO given = AuthorizationRequestDTO.builder()
                .name("bro")
                .email("bro@gmail.com")
                .build();

        Map<String, String> actual = given()
                .port(port)
                .contentType(ContentType.JSON)
                .when()
                .body(given)
                .post("/api/auth/signup")
                .prettyPeek()
                .then()
                .extract()
                .as(new TypeRef<>() {
                });

        assertThat(actual).isEqualTo(expected);
    }
}
