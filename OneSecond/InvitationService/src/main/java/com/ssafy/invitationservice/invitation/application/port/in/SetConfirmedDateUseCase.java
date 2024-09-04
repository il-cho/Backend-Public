package com.ssafy.invitationservice.invitation.application.port.in;

public interface SetConfirmedDateUseCase {
    String setConfirmedDate(String invitationCode, String confirmedDate);
}
