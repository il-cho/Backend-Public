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
public class NaverUser {

    @Id
    private String id;
    private int userId;

    public static NaverUser createInstance(String id, int userId) {
        return NaverUser.builder().id(id).userId(userId).build();
    }
}
