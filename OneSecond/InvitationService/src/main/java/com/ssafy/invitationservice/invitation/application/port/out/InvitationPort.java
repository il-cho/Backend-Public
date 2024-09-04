package com.ssafy.invitationservice.invitation.application.port.out;

import com.ssafy.invitationservice.invitation.domain.Invitation;

import java.util.List;

public interface InvitationPort {
    String saveInvitation(Invitation invitation);
    Invitation viewInvitation(String invitationCode);
    List<Invitation> viewInvitationList(String inviter);
    Invitation modifyInvitation(Invitation invitation);
    int deleteInvitation(String invitationCode);
    String setConfirmedDate(String invitationCode, String confirmedDate);
}
