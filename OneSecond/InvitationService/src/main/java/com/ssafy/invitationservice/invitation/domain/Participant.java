package com.ssafy.invitationservice.invitation.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Participant {
    private String invitationCode;
    private int userId;
    private boolean attend;
    private boolean isPaid;

    @Builder
    public Participant(String invitationCode, int userId, boolean attend, boolean isPaid) {
        this.invitationCode = invitationCode;
        this.userId = userId;
        this.attend = attend;
        this.isPaid = isPaid;
    }
}
