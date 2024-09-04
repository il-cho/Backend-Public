package com.ssafy.chatservice.domain.chat.controller;

import com.ssafy.chatservice.domain.chat.dto.ChatResponse;
import com.ssafy.chatservice.domain.chat.dto.UserInfo;
import com.ssafy.chatservice.domain.chat.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    // TODO:
    /*
     * WebSocket Config 설정 : Netty가 기본 지원
     * Kafka Topic Pub/Sub
     * Controller Service Repository Entity
     * Spring Reactive MongoDB
     * application.yml : Kafka, MongoDB
     */

    private final ChatService chatService;

    // 채팅 내역 받기
    @GetMapping("/history")
    public Mono<ResponseEntity<List<ChatResponse>>> getChatHistory(@RequestParam String invitationCode,
                                                                   @RequestParam(required = false) String chatId) {
        log.debug("ChatController.getChatHistory() is called.");
        log.info("invitationCode={}, chatId={}", invitationCode, chatId);

        return chatService.getChatHistory(invitationCode, chatId).collectList().map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // 채팅 참여자 목록 받기
    @GetMapping("/userinfo/{invitationCode}")
    public Mono<ResponseEntity<List<UserInfo>>> getUserInfoList(@PathVariable String invitationCode) {
        log.info("ChatController.getUserInfoList() is called.");

        return chatService.getUserInfoList(invitationCode).collectList().map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}

