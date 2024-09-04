package com.ssafy.invitationservice.account.application.port.out;


import com.ssafy.invitationservice.account.adaptor.out.persistence.entity.AccountEntity;
import com.ssafy.invitationservice.account.domain.Account;

public interface AccountPort {
    Long saveAccount(AccountEntity of);
    Account findAccountByInvitationCode(String invitationCode);
    Account updateAccount(Account account);
    int deleteAccountByInvitationCode(String invitationCode);
}
