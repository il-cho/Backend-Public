package com.ssafy.invitationservice.invitation.adaptor.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttendInput {
    @NotBlank(message = "참석 여부는 필수 값입니다.")
    private boolean attend;
}
