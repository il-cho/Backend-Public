package com.ssafy.invitationservice.account.application.port.in;

import com.ssafy.invitationservice.account.domain.Account;

public interface UpdateAccountUseCase {
    Account updateAccount(Account account);
}
