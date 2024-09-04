package com.ssafy.invitationservice.invitation.application.port.in;

import com.ssafy.invitationservice.invitation.domain.Invitation;

public interface ModifyInvitationUseCase {
    Invitation modifyInvitation(Invitation invitation);
}
