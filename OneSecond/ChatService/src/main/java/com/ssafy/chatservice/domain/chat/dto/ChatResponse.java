package com.ssafy.chatservice.domain.chat.dto;

import com.ssafy.chatservice.domain.chat.collection.Chat;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class ChatResponse {
    private String chatId;
    private String invitationCode;
    private Long senderId;
    private String content;
    private String createAt;

    public static ChatResponse from(Chat chat) {
        return ChatResponse.builder()
                .chatId(chat.getChatId())
                .invitationCode(chat.getInvitationCode())
                .senderId(chat.getSenderId())
                .content(chat.getContent())
                .createAt(chat.getCreateAt())
                .build();
    }

}
