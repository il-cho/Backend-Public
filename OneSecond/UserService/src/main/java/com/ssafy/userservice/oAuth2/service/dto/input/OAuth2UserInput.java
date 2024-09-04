package com.ssafy.userservice.oAuth2.service.dto.input;

import com.ssafy.userservice.user.enumeration.OAuth2Platform;

public interface OAuth2UserInput {

    OAuth2Platform getPlatform();

    String getId();

    String getNickname();

    String getAccessToken();
}
