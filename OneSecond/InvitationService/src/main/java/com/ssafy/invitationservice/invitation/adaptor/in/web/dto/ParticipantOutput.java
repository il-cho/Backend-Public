package com.ssafy.invitationservice.invitation.adaptor.in.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParticipantOutput {
    private int userId;
    private String username;
    private boolean attend;
    private boolean paid;
    private int profile;

    @Builder
    public ParticipantOutput(int userId, String username, boolean attend, boolean paid, int profile) {
        this.userId = userId;
        this.username = username;
        this.attend = attend;
        this.paid = paid;
        this.profile = profile;
    }
}
