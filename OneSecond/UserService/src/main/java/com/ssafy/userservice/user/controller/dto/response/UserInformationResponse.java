package com.ssafy.userservice.user.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInformationResponse {
    private String id;
    private int profile;
    private String nickname;
}
