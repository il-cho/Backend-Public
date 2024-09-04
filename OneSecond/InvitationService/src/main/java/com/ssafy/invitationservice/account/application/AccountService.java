package com.ssafy.invitationservice.account.application;

import com.ssafy.invitationservice.account.application.port.in.CreateAccountUseCase;
import com.ssafy.invitationservice.account.application.port.in.DeleteAccountUseCase;
import com.ssafy.invitationservice.account.application.port.in.GetAccountUseCase;
import com.ssafy.invitationservice.account.application.port.in.UpdateAccountUseCase;
import com.ssafy.invitationservice.account.application.port.out.AccountPort;
import com.ssafy.invitationservice.account.domain.Account;
import com.ssafy.invitationservice.account.mapper.AccountMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountService implements CreateAccountUseCase, GetAccountUseCase, UpdateAccountUseCase, DeleteAccountUseCase {
    private final AccountPort accountPort;
    private final AccountMapper mapper;
    @Override
    public Long saveAccount(Account account) {
        return accountPort.saveAccount(mapper.toAccountEntity(account));
    }

    @Override
    public Account findAccountByInvitationCode(String invitationCode) {
        return accountPort.findAccountByInvitationCode(invitationCode);
    }


    @Transactional
    @Override
    public Account updateAccount(Account account) {
        return accountPort.updateAccount(account);
    }

    @Override
    public int deleteAccountByInvitationCode(String invitationCode) {
        return accountPort.deleteAccountByInvitationCode(invitationCode);
    }
}
