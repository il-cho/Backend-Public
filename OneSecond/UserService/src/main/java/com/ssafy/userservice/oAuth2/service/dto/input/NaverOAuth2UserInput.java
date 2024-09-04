package com.ssafy.userservice.oAuth2.service.dto.input;

import com.ssafy.userservice.user.enumeration.OAuth2Platform;
import lombok.Getter;

import java.util.Map;

@Getter
public class NaverOAuth2UserInput implements OAuth2UserInput {

    private final Map<String, Object> attributes;
    private final String accessToken;
    private final String id;
    private final String nickname;

    public NaverOAuth2UserInput(String accessToken, Map<String, Object> attributes) {
        this.accessToken = accessToken;
        this.attributes = (Map<String, Object>) attributes.get("response");
        this.id = (String) this.attributes.get("id");
        this.nickname = (String) this.attributes.get("nickname");
    }

    @Override
    public OAuth2Platform getPlatform() {
        return OAuth2Platform.NAVER;
    }

}
