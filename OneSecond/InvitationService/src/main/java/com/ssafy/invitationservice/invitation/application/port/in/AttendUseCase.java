package com.ssafy.invitationservice.invitation.application.port.in;

public interface AttendUseCase {
    boolean attend(int userId, String invitationCode, boolean attend);
}
