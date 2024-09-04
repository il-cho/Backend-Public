package com.ssafy.invitationservice.invitation.application.port.in;

import com.ssafy.invitationservice.invitation.adaptor.in.web.dto.ParticipantOutput;
import com.ssafy.invitationservice.invitation.domain.Participant;

import java.util.List;

public interface ViewParticipantListUseCase {
    List<Participant> viewParticipantList(String invitationCode);
    List<ParticipantOutput> viewParticipantOutputList(List<Participant> participants);
}
