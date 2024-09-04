package com.ssafy.invitationservice.invitation.adaptor.in.web.dto;

import com.ssafy.invitationservice.invitation.domain.Invitation;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateInvitationInput {
    @NotBlank(message = "생성자는 필수 값입니다.")
    private String inviter;

    @NotBlank(message = "초대 문구는 필수 값입니다.")
    private String title;

    private String description;

    private LocalDate confirmedDate;

    public Invitation toDomain() {
        return Invitation.builder()
                .inviter(inviter)
                .title(title)
                .description(description)
                .confirmedDate(confirmedDate)
                .build();
    }
}
