package com.ssafy.invitationservice.account.adaptor.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAccountDto {

    private Long id;

    @NotBlank(message = "초대장 코드를 입력해주세요.")
    private String invitationCode;

    private long price;
    @NotBlank(message = "은행 이름을 입력해주세요.")
    private String bankName;

    @NotBlank(message = "은행 계좌를 입력해주세요.")
    private String bankAccount;

    @NotBlank(message = "예금주를 입력해주세요.")
    private String accountHolder;
}
