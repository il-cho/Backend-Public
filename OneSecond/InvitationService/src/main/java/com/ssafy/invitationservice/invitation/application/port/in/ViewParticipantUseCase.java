package com.ssafy.invitationservice.invitation.application.port.in;

public interface ViewParticipantUseCase {
    Boolean viewParticipantUseCase(String invitationCode, int loginId);
}
