package com.ssafy.invitationservice.account.adaptor.out.persistence;

import com.ssafy.invitationservice.account.adaptor.out.persistence.entity.AccountEntity;
import com.ssafy.invitationservice.account.adaptor.out.persistence.repository.AccountRepository;
import com.ssafy.invitationservice.account.application.port.out.AccountPort;
import com.ssafy.invitationservice.account.domain.Account;
import com.ssafy.invitationservice.account.mapper.AccountMapper;
import com.ssafy.invitationservice.global.error.ErrorCode;
import com.ssafy.invitationservice.global.error.exception.AccountException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class AccountPersistenceAdaptor implements AccountPort {
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;
    @Override
    public Long saveAccount(AccountEntity accountEntity) {
        return accountRepository.save(accountEntity).getId();
    }

    @Override
    public Account findAccountByInvitationCode(String invitationCode) {
        return mapper.toAccount(
                accountRepository
                .findAccountByInvitationCode(invitationCode)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND))
        ); // 추후에 수정
    }

    @Transactional
    @Override
    public Account updateAccount(Account account) {
        AccountEntity findAccountEntity = accountRepository
                .findAccountByInvitationCode(account.getInvitationCode())
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));
        findAccountEntity.changeInfo(account);
        return  mapper.toAccount(findAccountEntity);
    }

    @Override
    public int deleteAccountByInvitationCode(String invitationCode) {
        return accountRepository.deleteByInvitationCode(invitationCode);
    }
}
