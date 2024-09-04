package com.ssafy.userservice.user.entity;

import com.ssafy.userservice.user.enumeration.OAuth2Platform;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private OAuth2Platform platform;

    public static User createInstance(OAuth2Platform platform) {
        return User.builder()
                .platform(platform)
                .build();
    }

}
