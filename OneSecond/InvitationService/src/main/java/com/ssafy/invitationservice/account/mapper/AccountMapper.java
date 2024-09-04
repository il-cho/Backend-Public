package com.ssafy.invitationservice.account.mapper;

import com.ssafy.invitationservice.account.adaptor.in.web.dto.CreateAccountDto;
import com.ssafy.invitationservice.account.adaptor.in.web.dto.UpdateAccountDto;
import com.ssafy.invitationservice.account.adaptor.out.persistence.entity.AccountEntity;
import com.ssafy.invitationservice.account.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    public Account toAccount(CreateAccountDto createAccountDto);
    public Account toAccount(UpdateAccountDto updateAccountDto);
    public Account toAccount(AccountEntity accountEntity);
    public AccountEntity toAccountEntity(Account account);
    public CreateAccountDto toCreateAccountDto(Account account);
    public UpdateAccountDto toUpdateAccountDto(Account account);
}
