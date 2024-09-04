package com.ssafy.invitationservice.invitation.application;

import com.ssafy.invitationservice.invitation.application.port.in.*;
import com.ssafy.invitationservice.invitation.application.port.out.InvitationPort;
import com.ssafy.invitationservice.invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvitationService implements CreateInvitationUseCase, ViewInvitationUseCase, ViewInvitationListUseCase,
        DeleteInvitationUseCase, ModifyInvitationUseCase, SetConfirmedDateUseCase {
    private final InvitationPort invitationPort;

    @Override
    @Transactional
    public String createInvitation(Invitation invitation) {
        return invitationPort.saveInvitation(invitation);
    }

    @Override
    public Invitation viewInvitation(String invitationCode) {
        return invitationPort.viewInvitation(invitationCode);
    }

    @Override
    public List<Invitation> viewInvitationList(String userId) {
        return invitationPort.viewInvitationList(userId);
    }

    @Override
    @Transactional
    public int deleteInvitation(String invitationCode) {
        return invitationPort.deleteInvitation(invitationCode);
    }

    @Override
    @Transactional
    public Invitation modifyInvitation(Invitation invitation) {
        return invitationPort.modifyInvitation(invitation);
    }

    @Override
    @Transactional
    public String setConfirmedDate(String invitationCode, String confirmedDate) {
        return invitationPort.setConfirmedDate(invitationCode, confirmedDate);
    }
}
