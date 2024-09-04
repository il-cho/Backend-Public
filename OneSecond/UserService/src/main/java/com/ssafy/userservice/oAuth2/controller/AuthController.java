package com.ssafy.userservice.oAuth2.controller;

import com.ssafy.userservice.oAuth2.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final CustomOAuth2UserService customOAuth2UserService;

    @PostMapping("/reissue")
    public ResponseEntity<String> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        log.info("reissueToken");
        return new ResponseEntity<>(customOAuth2UserService.updateAccessToken(request, response), HttpStatus.OK);
    }
}
