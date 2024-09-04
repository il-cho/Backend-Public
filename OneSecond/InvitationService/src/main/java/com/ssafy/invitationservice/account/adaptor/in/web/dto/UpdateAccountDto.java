package com.ssafy.invitationservice.account.adaptor.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateAccountDto {
    @NotNull(message = "계좌 ID를 입력해주세요.")
    private Long id;

    @NotBlank(message = "초대장 코드를 입력해주세요.")
    private String invitationCode;

    private long price;

    private String bankName;

    private String bankAccount;

    private String accountHolder;
}
