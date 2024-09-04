package com.ssafy.chatservice.domain.chat.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.ssafy.chatservice.domain.chat.collection.Chat;
import com.ssafy.chatservice.domain.chat.dto.ChatRequest;
import com.ssafy.chatservice.domain.chat.dto.ChatResponse;
import com.ssafy.chatservice.domain.chat.dto.UserInfo;
import com.ssafy.chatservice.domain.chat.repository.ChatRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ChatServiceImplTest {

    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private ChatServiceImpl chatService;

    @Mock
    private WebClient mockWebClient;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> mockRequestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> mockRequestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec mockResponseSpec;

    private String invitationCode = "invitation_code";
    private Long senderId = 1L;
    private String content = "Hello World!";
    private ChatRequest chatRequest;
    private Chat chat;
    private ChatResponse chatResponse;
    private String username = "nickname";
    private int profile = 1;
    private UserInfo userInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        chatRequest = ChatRequest.builder().invitationCode(invitationCode).senderId(senderId).content(content).build();

        chat = ChatRequest.toEntity(chatRequest);
        chatResponse = ChatResponse.from(chat);

        userInfo = UserInfo.builder().userId(senderId).username(username).profile(profile).build();

    }

    @Test
    @DisplayName("채팅_저장_성공")
    void saveChatSuccess() {
        // Given
        when(chatRepository.save(any(Chat.class))).thenReturn(Mono.just(chat));

        // When
        StepVerifier.create(chatService.save(chatRequest)).expectNext(chat) // Then
                .verifyComplete();
    }

    @Test
    @DisplayName("채팅_내역_조회_성공")
    void getChatHistorySuccess() {
        // Given
        String chatId = new ObjectId().toHexString();
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "_id"));
        when(chatRepository.findByInvitationCodeAndIdLessThan(anyString(), any(ObjectId.class),
                any(Pageable.class))).thenReturn(Flux.just(chat));

        // When
        StepVerifier.create(chatService.getChatHistory(invitationCode, chatId)).expectNext(chatResponse)// Then
                .verifyComplete();
    }

    @Test
    @DisplayName("채팅_내역_조회_성공_ChatID_없음")
    void getChatHistoryWithNullChatIdSuccess() {
        // Given
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "_id"));
        when(chatRepository.findAllByInvitationCode(anyString(), any(Pageable.class))).thenReturn(Flux.just(chat));

        // When
        StepVerifier.create(chatService.getChatHistory(invitationCode, null))
                .expectNext(ChatResponse.from(chat)) // Then
                .verifyComplete();
    }

    @Test
    @DisplayName("채팅 참여자 목록 조회 성공")
    void getUserInfoListSuccess() {
        // Given
        // WebClient 체이닝 설정
        when(mockWebClient.get()).thenAnswer(invocation -> mockRequestHeadersUriSpec);
        when(mockRequestHeadersUriSpec.uri(anyString())).thenAnswer(invocation -> mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenAnswer(invocation -> mockResponseSpec);
        when(mockResponseSpec.bodyToFlux(UserInfo.class)).thenReturn(Flux.just(userInfo));

        // When
        StepVerifier.create(chatService.getUserInfoList(invitationCode))
                .expectNext(userInfo)// Then
                .verifyComplete();

        // Verify that WebClient chain was called correctly
        Mockito.verify(mockWebClient).get();
        Mockito.verify(mockRequestHeadersUriSpec).uri(anyString());
        Mockito.verify(mockRequestHeadersSpec).retrieve();
        Mockito.verify(mockResponseSpec).bodyToFlux(UserInfo.class);
    }
}
