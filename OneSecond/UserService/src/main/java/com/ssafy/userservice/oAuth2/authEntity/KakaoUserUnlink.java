package com.ssafy.userservice.oAuth2.authEntity;

import com.ssafy.userservice.oAuth2.client.KakaoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoUserUnlink {

    private final KakaoClient kakaoClient;

    public void unlink(String accessToken) {
        kakaoClient.unlink("Bearer " + accessToken);
    }
}
