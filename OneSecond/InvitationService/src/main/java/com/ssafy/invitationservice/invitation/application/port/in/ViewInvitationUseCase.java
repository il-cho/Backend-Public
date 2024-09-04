package com.ssafy.invitationservice.invitation.application.port.in;

import com.ssafy.invitationservice.invitation.domain.Invitation;

public interface ViewInvitationUseCase {
    Invitation viewInvitation(String invitationCode);
}
