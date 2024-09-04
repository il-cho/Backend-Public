package com.ssafy.chatservice.domain.chat.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserInfo {
    private Long userId;
    private String username;
    private int profile;
}
