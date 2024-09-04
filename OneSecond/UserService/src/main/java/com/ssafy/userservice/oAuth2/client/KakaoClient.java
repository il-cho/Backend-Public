package com.ssafy.userservice.oAuth2.client;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoClient", url = "https://kapi.kakao.com")
public interface KakaoClient {

    @PostMapping("/v1/user/unlink")
    void unlink(@RequestHeader("Authorization") String bearerToken);
}