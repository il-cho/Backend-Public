package com.ssafy.chatservice.domain.chat.service;

import com.ssafy.chatservice.domain.chat.collection.Chat;
import com.ssafy.chatservice.domain.chat.dto.ChatRequest;
import com.ssafy.chatservice.domain.chat.dto.ChatResponse;
import com.ssafy.chatservice.domain.chat.dto.UserInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatService {
    Mono<Chat> save(ChatRequest chatRequest);

    Flux<ChatResponse> getChatHistory(String inviteCode, String chatId);

    Flux<UserInfo> getUserInfoList(String inviteCode);
}
