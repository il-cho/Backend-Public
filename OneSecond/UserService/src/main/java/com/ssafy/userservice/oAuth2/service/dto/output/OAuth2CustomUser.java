package com.ssafy.userservice.oAuth2.service.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class OAuth2CustomUser implements OAuth2UserOutput{

    private final int id;
    private String accessToken;

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}