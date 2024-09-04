package com.ssafy.invitationservice.invitation.application.port.in;

import com.ssafy.invitationservice.invitation.domain.Invitation;

import java.util.List;

public interface ViewInvitationListUseCase {
    List<Invitation> viewInvitationList(String inviter);
}
