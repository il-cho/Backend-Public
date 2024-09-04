package com.ssafy.userservice.user.controller.dto.request;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private String nickname;
    private int profile;
}
