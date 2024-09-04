package com.ssafy.chatservice.domain.hello.controller;

import com.ssafy.chatservice.domain.chat.handler.CustomWebSocketHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(HelloController.class)
public class HelloControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CustomWebSocketHandler handler;

    @Test
    @DisplayName("API 연결 테스트")
    public void helloTest() {
        webTestClient.get().uri("/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello, World!");
    }
}
