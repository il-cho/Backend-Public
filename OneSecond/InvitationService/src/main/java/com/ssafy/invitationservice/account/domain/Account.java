package com.ssafy.invitationservice.account.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Account {

    private Long id;

    private String invitationCode;

    private long price;

    private String bankName;

    private String bankAccount;

    private String accountHolder;
}
