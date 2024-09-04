package com.ssafy.chatservice.domain.chat.service.impl;

import com.ssafy.chatservice.domain.chat.collection.Chat;
import com.ssafy.chatservice.domain.chat.dto.ChatRequest;
import com.ssafy.chatservice.domain.chat.dto.ChatResponse;
import com.ssafy.chatservice.domain.chat.dto.UserInfo;
import com.ssafy.chatservice.domain.chat.repository.ChatRepository;
import com.ssafy.chatservice.domain.chat.service.ChatService;
import java.util.Comparator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private static final String INVITATION_SERVICE_URI = "/invitation/participant/list/"; // URI 지정
    private final WebClient webClient;

    @Override
    public Mono<Chat> save(ChatRequest chatRequest) {
        log.info("ChatServiceImpl.save() is called.");

        Chat chat = ChatRequest.toEntity(chatRequest); // DTO -> Collection 변환
        return chatRepository.save(chat) // Mono<Chat> 반환
                .doOnError(error -> log.error("Failed to save chat: {}", error.getMessage()));
    }

    // 채팅 내역 가져오기
    @Override
    public Flux<ChatResponse> getChatHistory(String invitationCode, String chatId) {
        log.info("ChatServiceImpl.getChatHistory() is called.");
        log.info("InvitationCode={}, chatID={}", invitationCode, chatId);

        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "_id")); // 최신 데이터를 기준으로 20개 가져오기
        Flux<Chat> chatFlux = Optional.ofNullable(chatId)
                .map(String::trim)
                .filter(id -> !id.isEmpty())
                .filter(id -> id.length() == 24) // ObjectId가 24자리의 16진수 문자열이어야 함을 확인
                .map(id -> {
                    log.info("ChatId is not null.");
                    ObjectId chatObjectIdObj = new ObjectId(id);
                    return chatRepository.findByInvitationCodeAndIdLessThan(invitationCode, chatObjectIdObj, pageable);
                })
                .orElseGet(() -> {
                    log.info("ChatId is null or empty.");
                    return chatRepository.findAllByInvitationCode(invitationCode, pageable);
                })
                .sort(Comparator.comparing(Chat::getChatId));

        return chatFlux
                .doOnSubscribe(message -> log.info("Success to find dataset."))
                .map(ChatResponse::from) // Collection -> DTO 변환
                .doOnError(e -> log.error("Error! : {}", e.getMessage()));
    }

    // 채팅 참여자 목록
    @Override
    public Flux<UserInfo> getUserInfoList(String invitationCode) {
        log.info("ChatService.getUserInfoList() is called.");
        log.info("{}", invitationCode);

        // Invitation-service에서 참석자 목록 호출
        Flux<UserInfo> members = webClient.get()
                .uri(INVITATION_SERVICE_URI + invitationCode)
                .retrieve()
                .bodyToFlux(UserInfo.class);

        return members;
    }
}
