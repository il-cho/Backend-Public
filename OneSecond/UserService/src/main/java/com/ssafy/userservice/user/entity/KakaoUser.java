package com.ssafy.userservice.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoUser {

    @Id
    private String id;
    private int userId;

    public static KakaoUser createInstance(String id, int userId) {
        return KakaoUser.builder().id(id).userId(userId).build();
    }
}
