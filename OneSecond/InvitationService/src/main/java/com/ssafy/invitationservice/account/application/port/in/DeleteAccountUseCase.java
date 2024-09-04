package com.ssafy.invitationservice.account.application.port.in;

public interface DeleteAccountUseCase {
    int deleteAccountByInvitationCode(String invitationCode);
}
