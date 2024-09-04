package com.ssafy.userservice.oAuth2.client;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverClient", url = "https://nid.naver.com")
public interface NaverClient {

    @PostMapping("/oauth2.0/token")
    void unlink(@RequestParam("grant_type") String grantType,
                @RequestParam("client_id") String clientId,
                @RequestParam("client_secret") String clientSecret,
                @RequestParam("access_token") String accessToken);
}
