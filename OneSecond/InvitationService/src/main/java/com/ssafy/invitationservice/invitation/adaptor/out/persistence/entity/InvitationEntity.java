package com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity;

import com.ssafy.invitationservice.invitation.domain.Invitation;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "invitation")
public class InvitationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long id;

    @Column(name = "inviter_id")
    private String inviter;

    private String title;

    private String description;

    private LocalDate confirmedDate;

    @Column(name = "invitation_code")
    private String invitationCode;

    public static InvitationEntity of(Invitation invitation, String invitationCode){
        InvitationEntity entity = new InvitationEntity();
        entity.inviter = invitation.getInviter();
        entity.title = invitation.getTitle();
        entity.description = invitation.getDescription();
        entity.confirmedDate = invitation.getConfirmedDate();
        entity.invitationCode = invitationCode;

        return entity;
    }

    public void changeInfo(Invitation invitation){
        this.title = invitation.getTitle();
        this.description = invitation.getDescription();
        this.confirmedDate = invitation.getConfirmedDate();
    }

    public String setConfirmedDate(String confirmedDate){
        this.confirmedDate = LocalDate.parse(confirmedDate);
        return this.confirmedDate.toString();
    }
}
