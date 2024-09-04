package com.ssafy.invitationservice.invitation.adaptor.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateFeatureInput {

    private String invitationCode;

    private boolean schedule;
    private boolean place;
    private boolean account;
    private boolean chat;
    private boolean chatbot;
}
