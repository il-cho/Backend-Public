package com.ssafy.userservice.oAuth2.authUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${spring.security.oauth2.jwt.secretkey}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(key.getBytes(StandardCharsets.UTF_8))
                .getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String id, long expiration) {
        Date exp = new Date(System.currentTimeMillis() + expiration); // 하루
        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .claim("id", id)
                .expiration(exp)
                .signWith(secretKey)
                .compact();
    }

}
