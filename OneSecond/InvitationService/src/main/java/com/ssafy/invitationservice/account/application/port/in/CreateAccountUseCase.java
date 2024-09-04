package com.ssafy.invitationservice.account.application.port.in;

import com.ssafy.invitationservice.account.domain.Account;

public interface CreateAccountUseCase {
    Long saveAccount(Account account);
}
