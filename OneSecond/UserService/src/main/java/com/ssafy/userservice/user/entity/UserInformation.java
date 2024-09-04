package com.ssafy.userservice.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInformation {

    @Id
    private int id;
    private String nickname;
    private int profile;
    private String registDate;
    private String updateDate;

    public static UserInformation createInstance(int userId, String nickname) {
        return UserInformation.builder().id(userId)
                .nickname(nickname)
                .profile(1)
                .registDate(LocalDateTime.now().toString())
                .updateDate(LocalDateTime.now().toString())
                .build();
    }

    public void updated() {
        this.updateDate = LocalDateTime.now().toString();
    }
}
