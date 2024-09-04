package com.ssafy.chatservice.domain.chat.dto;

import com.ssafy.chatservice.domain.chat.collection.Chat;
import com.ssafy.chatservice.global.error.InvalidChatRequestException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ChatRequest {
    private String invitationCode;
    private Long senderId;
    private String content;

    // 유효성 검사 메서드
    public void validate() {
        if (invitationCode == null || invitationCode.trim().isEmpty()) {
            throw new InvalidChatRequestException("Invitation code cannot be null or empty.");
        }
        if (senderId == null) {
            throw new InvalidChatRequestException("Sender ID cannot be null.");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new InvalidChatRequestException("Content cannot be null or empty.");
        }
    }

    public static Chat toEntity(ChatRequest chatRequest) {
        // 유효성 검사 호출
        chatRequest.validate();

        return Chat.builder()
                .invitationCode(chatRequest.getInvitationCode())
                .senderId(chatRequest.getSenderId())
                .content(chatRequest.getContent())
                .createAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toString())
                .build();
    }
}
