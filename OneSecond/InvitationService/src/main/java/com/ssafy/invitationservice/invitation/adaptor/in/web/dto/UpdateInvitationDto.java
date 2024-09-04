package com.ssafy.invitationservice.invitation.adaptor.in.web.dto;

import com.ssafy.invitationservice.invitation.domain.Invitation;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateInvitationDto {
    @NotBlank(message = "초대장 코드는 필수 값입니다.")
    private String invitationCode;

    @NotBlank(message = "생성자는 필수 값입니다.")
    private String inviter;

    @NotBlank(message = "초대 문구는 필수 값입니다.")
    private String title;

    private String description;

    public Invitation toDomain() {
        return Invitation.builder()
                .invitationCode(invitationCode)
                .inviter(inviter)
                .title(title)
                .description(description)
                .build();
    }
}
