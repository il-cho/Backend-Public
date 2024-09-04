package com.ssafy.invitationservice.account.adaptor.out.persistence.repository;


import com.ssafy.invitationservice.account.adaptor.out.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends JpaRepository <AccountEntity, Long> {
    Optional<AccountEntity> findAccountByInvitationCode(String invitationCode);

    @Transactional
    int deleteByInvitationCode(String invitationCode);
}
