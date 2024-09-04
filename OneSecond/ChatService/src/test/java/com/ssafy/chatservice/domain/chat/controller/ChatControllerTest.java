package com.ssafy.chatservice.domain.chat.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.ssafy.chatservice.domain.chat.dto.ChatResponse;
import com.ssafy.chatservice.domain.chat.dto.UserInfo;
import com.ssafy.chatservice.domain.chat.handler.CustomWebSocketHandler;
import com.ssafy.chatservice.domain.chat.service.ChatService;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest(ChatController.class)
public class ChatControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ChatService chatService;

    @MockBean
    private CustomWebSocketHandler handler;

    private final String chatId = "hashValue";
    private final String invitationCode = "invitation_code";
    private final Long senderId = 1L;
    private final String content = "Hello World Test!";
    private final String username = "nickname";
    private final int profile = 1;
    private ChatResponse chatResponse;
    private UserInfo userInfo;

    @BeforeEach
    void setUp() {
        chatResponse = ChatResponse.builder()
                .chatId(chatId)
                .invitationCode(invitationCode)
                .senderId(senderId)
                .content(content)
                .createAt(LocalDateTime.now().toString())
                .build();

        userInfo = UserInfo.builder()
                .userId(senderId)
                .username(username)
                .profile(profile)
                .build();
    }

    @Test
    @DisplayName("채팅_내역_불러오기_성공")
    void getChatHistorySuccess() {
        // Given
        List<ChatResponse> chatResponses = Collections.singletonList(chatResponse);
        when(chatService.getChatHistory(anyString(), anyString()))
                .thenReturn(Flux.fromIterable(chatResponses));

        // When
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/chat/history")
                        .queryParam("invitationCode", invitationCode)
                        .queryParam("chatId", chatId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ChatResponse.class)
                .isEqualTo(chatResponses);
    }

    @Test
    @DisplayName("채팅_내역_불러오기_성공_ChatID_없음")
    void getChatHistoryFailure() {
        // Given
        List<ChatResponse> chatResponses = Collections.singletonList(chatResponse);
        when(chatService.getChatHistory(anyString(), eq(null)))
                .thenReturn(Flux.fromIterable(chatResponses));

        // When
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/chat/history")
                        .queryParam("invitationCode", invitationCode)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk() // Then
                .expectBodyList(ChatResponse.class)
                .isEqualTo(chatResponses);
    }

    @Test
    @DisplayName("참여자_리스트_조회_성공")
    void getUserInfoListSuccess() {
        // Given
        List<UserInfo> userInfos = Collections.singletonList(userInfo);
        when(chatService.getUserInfoList(anyString()))
                .thenReturn(Flux.fromIterable(userInfos));

        // When
        webTestClient.get()
                .uri("/chat/userinfo/testCode")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk() // Then
                .expectBodyList(UserInfo.class)
                .isEqualTo(userInfos);
    }
}
