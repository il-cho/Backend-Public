package com.ssafy.invitationservice.account.application.port.in;

import com.ssafy.invitationservice.account.domain.Account;

public interface GetAccountUseCase {

    Account findAccountByInvitationCode(String invitationCode);
}
