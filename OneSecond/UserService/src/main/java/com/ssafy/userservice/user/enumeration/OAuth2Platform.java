package com.ssafy.userservice.user.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2Platform {
    KAKAO("kakao"),
    NAVER("naver");

    private final String registrationId;
}
