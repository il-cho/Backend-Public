package com.ssafy.userservice.oAuth2.authUtil;

import com.ssafy.userservice.oAuth2.service.dto.output.OAuth2CustomUser;
import com.ssafy.userservice.user.enumeration.OAuth2Platform;
import com.ssafy.userservice.oAuth2.authEntity.KakaoUserUnlink;
import com.ssafy.userservice.oAuth2.authEntity.NaverUserUnlink;
import com.ssafy.userservice.oAuth2.service.dto.output.OAuth2UserOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnlinkUtil {

    private final KakaoUserUnlink kakaoUserUnlink;
    private final NaverUserUnlink naverUserUnlink;


    public void unlink(OAuth2CustomUser authUser, OAuth2Platform platform) {

        if(OAuth2Platform.KAKAO.equals(platform)) {
            kakaoUserUnlink.unlink(authUser.getAccessToken());
        } else if(OAuth2Platform.NAVER.equals(platform)){
            naverUserUnlink.unlink(authUser.getAccessToken());
        } else {
            throw new IllegalArgumentException("Unsupported platform");
        }
    }
}
