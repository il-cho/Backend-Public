package com.ssafy.userservice.oAuth2.authEntity;

import com.ssafy.userservice.oAuth2.client.NaverClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NaverUserUnlink {

    private final NaverClient naverClient;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

    public void unlink(String accessToken) {
        naverClient.unlink("delete", clientId, clientSecret, accessToken);
    }


}
