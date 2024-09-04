package com.ssafy.invitationservice.account.adaptor.out.persistence.entity;

import com.ssafy.invitationservice.account.domain.Account;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccountEntity {

    public AccountEntity(String invitationCode, Long price, String bankName, String bankAccount, String accountHolder){
        this.invitationCode = invitationCode;
        this.price = price;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.accountHolder = accountHolder;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invitationCode;

    private long price;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private String bankAccount;

    @Column(nullable = false)
    private String accountHolder;

    public void changeInfo(Account account) {
        if(account.getPrice() != 0) this.price = account.getPrice();
        if(StringUtils.hasText(account.getBankName())) this.bankName = account.getBankName();
        if(StringUtils.hasText(account.getBankAccount())) this.bankAccount = account.getBankAccount();
        if(StringUtils.hasText(account.getAccountHolder())) this.accountHolder = account.getAccountHolder();
    }
}
