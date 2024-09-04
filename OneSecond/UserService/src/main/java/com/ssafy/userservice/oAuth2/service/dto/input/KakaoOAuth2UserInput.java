package com.ssafy.userservice.oAuth2.service.dto.input;

import com.ssafy.userservice.user.enumeration.OAuth2Platform;
import lombok.Getter;

import java.util.Map;

@Getter
public class KakaoOAuth2UserInput implements OAuth2UserInput {

    private final Map<String, Object> attributes;
    private final String accessToken;
    private final String id;
    private final String nickname;

    public KakaoOAuth2UserInput(String accessToken, Map<String, Object> attributes) {
        this.accessToken = accessToken;
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        this.attributes = (Map<String, Object>) kakaoAccount.get("profile");
        this.id = ((Long) attributes.get("id")).toString();
        this.nickname = (String) this.attributes.get("nickname");
        this.attributes.put("id", this.getId());
    }

    @Override
    public OAuth2Platform getPlatform() {
        return OAuth2Platform.KAKAO;
    }

}
