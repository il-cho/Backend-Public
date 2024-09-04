package com.ssafy.userservice.oAuth2.authUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Optional;

@Component
public class CookieUtil {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    public static String serialize(Object object) throws JsonProcessingException {
        return Base64.getUrlEncoder()
                .encodeToString(objectMapper.writeValueAsString(object).getBytes());
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) throws JsonProcessingException {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(cookie.getValue());
        String decodedString = new String(decodedBytes);
        return objectMapper.readValue(decodedString, cls);
    }

    public ResponseCookie generateRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refresh", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(2592000)
                .sameSite("None")
                .build();
    }
}
