package com.ssafy.userservice.oAuth2.service.dto.output;

import com.ssafy.userservice.user.enumeration.OAuth2Platform;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public interface OAuth2UserOutput extends OAuth2User, UserDetails {

    String getAccessToken();

    @Override
    default Map<String, Object> getAttributes() {
        return Collections.emptyMap();
    };

    @Override
    default boolean isAccountNonExpired() {
        return true;
    }

    @Override
    default boolean isAccountNonLocked() {
        return true;
    }

    @Override
    default boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    default boolean isEnabled() { return true; }

    @Override
    default Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    default String getPassword() {
        return null;
    }

}
