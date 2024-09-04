package com.ssafy.invitationservice.invitation.adaptor.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateInvitationOutput {
    private String fileUrl;
    private String invitationCode;
}
