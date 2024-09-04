package com.ssafy.chatservice.domain.chat.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@AllArgsConstructor
@Builder
@Document(collection = "chat")
public class Chat {
    @Id
    private String chatId;
    private String invitationCode;
    private Long senderId;
    private String content;
    private String createAt;
}
