package com.ssafy.userservice.oAuth2.service.dto.input;

import com.ssafy.userservice.user.enumeration.OAuth2Platform;

import java.util.Map;

public class OAuth2UserInputFactory {

    public static OAuth2UserInput getOAuth2UserInput(String registrationId,
                                                     String accessToken,
                                                     Map<String, Object> attributes) {
       if (OAuth2Platform.KAKAO.getRegistrationId().equals(registrationId)) {
            return new KakaoOAuth2UserInput(accessToken, attributes);
       } else if (OAuth2Platform.NAVER.getRegistrationId().equals(registrationId)) {
           return new NaverOAuth2UserInput(accessToken, attributes);
       } else {
            throw new IllegalArgumentException("Unsupported platform");
       }
    }
}