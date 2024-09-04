package com.ssafy.gatewayservice;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    private final SecretKey secretKey;

    public AuthorizationHeaderFilter(@Value("${jwt.token.secret}") String key) {
        super(Config.class);
        this.secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(key.getBytes(StandardCharsets.UTF_8))
                .getBytes(StandardCharsets.UTF_8));
    }

    @NoArgsConstructor
    public static class Config {
    }

    @Override
    public GatewayFilter apply(AuthorizationHeaderFilter.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Authorization 헤더가 없으면 에러 응답 
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            // Authorization 헤더에서 JWT 추출
            String authorizationHeader = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            if (!authorizationHeader.startsWith("Bearer ")) {
                return onError(exchange, "Invalid authorization header format", HttpStatus.UNAUTHORIZED);
            }
            String jwt = authorizationHeader.substring(7); // "Bearer " 다음
            log.info("JWT -> {}", jwt);

            // JWT 검증 및 사용자 ID 추출
            String loginId = isJwtValidAndId(jwt);

            if (loginId == null || loginId.isEmpty()) {
                return onError(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED);
            }

            // 요청에 사용자 ID 추가
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("loginId", loginId)
                    .build();

            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();

            // 필터 체인을 통해 다음 필터로 요청을 전달
            return chain.filter(modifiedExchange);
        };
    }

    private String isJwtValidAndId(String jwt) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build().parseClaimsJws(jwt);

            Claims claims = claimsJws.getBody();
            
            // JWT 유효성 검사
            if (claims.getExpiration().before(new Date())) { // 유효기간 지나면
                return null;
            }

            // 사용자 ID 반환
            return claims.get("id", String.class);
        } catch (ExpiredJwtException e) {
            log.error("JWT expired", e);
            return null;
        } catch (UnsupportedJwtException e) {
            log.error("JWT not supported", e);
            return null;
        } catch (MalformedJwtException e) {
            log.error("JWT is malformed", e);
            return null;
        } catch (SignatureException e) {
            log.error("JWT signature does not match", e);
            return null;
        } catch (Exception e) {
            log.error("JWT validation failed", e);
            return null;
        }
    }

    // mono, flux -> webflux : 단위 값이면 mono로 반환
    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(error);

        return response.setComplete();
    }
}
