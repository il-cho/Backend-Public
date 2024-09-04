package com.ssafy.invitationservice.invitation.domain;

import com.ssafy.invitationservice.invitation.adaptor.in.web.dto.UpdateFeatureInput;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Feature {

    private String invitationCode;
    private boolean schedule;
    private boolean place;
    private boolean account;
    private boolean chat;
    private boolean chatbot;

    @Builder
    public Feature(String invitationCode, boolean schedule, boolean place, boolean account, boolean chat, boolean chatbot) {
        this.invitationCode = invitationCode;
        this.schedule = schedule;
        this.place = place;
        this.account = account;
        this.chat = chat;
        this.chatbot = chatbot;
    }
}
