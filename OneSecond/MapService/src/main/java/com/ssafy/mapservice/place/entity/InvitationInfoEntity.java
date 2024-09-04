package com.ssafy.mapservice.place.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "invitation_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_info_id")
    private Long id;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String invitationCode;

    public InvitationInfoEntity(String userId, String invitationCode) {
        this.userId = userId;
        this.invitationCode = invitationCode;
    }
}
