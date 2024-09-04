package com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "participant")
public class ParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private InvitationEntity invitation;

    @Column(name = "invitation_code", nullable = false)
    private String invitationCode;

    @Column(name = "user_id", nullable = false)
    private int userId;

    private boolean attend;

    @Column(name = "is_paid")
    private boolean isPaid;

    public ParticipantEntity changeAttend(boolean attend) {
        this.attend = attend;
        return this;
    }
}

