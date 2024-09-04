package com.ssafy.invitationservice.invitation.application.port.out;

import com.ssafy.invitationservice.invitation.domain.Participant;

import java.util.List;

public interface ParticipantPort {
    List<Participant> viewParticipantList(String invitationCode);
    Boolean viewParticipant(String invitationCode, int loginId);
    Participant modifyAttend(Participant participant);
}
