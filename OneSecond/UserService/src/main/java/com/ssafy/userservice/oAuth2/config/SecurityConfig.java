package com.ssafy.userservice.oAuth2.config;

import com.ssafy.userservice.oAuth2.authrepository.CustomAuthorizationRequestRepository;
import com.ssafy.userservice.oAuth2.filter.TokenAuthenticationFilter;
import com.ssafy.userservice.oAuth2.handler.CustomLogoutHandler;
import com.ssafy.userservice.oAuth2.handler.CustomOauth2FailureHandler;
import com.ssafy.userservice.oAuth2.handler.CustomOauth2SuccessHandler;
import com.ssafy.userservice.oAuth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthorizationRequestRepository  customAuthorizationRequestRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOauth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomOauth2FailureHandler customOAuth2FailureHandler;
    private final CustomLogoutHandler customLogoutHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .headers(headersConfigurer -> headersConfigurer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(antMatcher("/auth/reissue")).permitAll()
                        .requestMatchers(antMatcher("/user/chat")).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(logout -> logout.logoutUrl("/oauth2/authorization/logout")
                        .addLogoutHandler(customLogoutHandler)
                        .logoutSuccessUrl("https://il-cho.site/")
                )
                .oauth2Login(configure -> configure
                        .authorizationEndpoint(config -> config.authorizationRequestRepository(customAuthorizationRequestRepository))
                        .userInfoEndpoint(config -> config.userService(customOAuth2UserService))
                        .successHandler(customOAuth2SuccessHandler)
                        .failureHandler(customOAuth2FailureHandler)
                );
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

