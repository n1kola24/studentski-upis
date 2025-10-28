package com.example.apigateway.Test;

import com.example.apigateway.config.ApiKeyFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


@WebFluxTest
@Import({ApiKeyFilter.class, ApiKeyTest.TestConfig.class})
@TestPropertySource(properties = "gateway.api.key=NikolaZivadinovic552022")
public class ApiKeyTest {

    @Autowired
    private WebTestClient webTestClient;

    public static class TestConfig {
        @Bean
        public RouterFunction<ServerResponse> testRoute() {
            return route(GET("/api/students"),
                    request -> ServerResponse.ok().bodyValue("Test uspesan"));
        }
    }

    @Test
    void whenNoApiKey_thenUnauthorized() {
        webTestClient.get()
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void whenInvalidApiKey_thenUnauthorized() {
        webTestClient.get()
                .uri("/api/students")
                .header("x-api-key", "NikolaZivadinovic")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void whenValidApiKey_thenOk() {
        webTestClient.get()
                .uri("/api/students")
                .header("x-api-key", "NikolaZivadinovic552022")
                .exchange()
                .expectStatus().isOk();
    }
}