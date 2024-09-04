package com.ssafy.invitationservice.invitation.adaptor.out.persistence;

import com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity.InvitationEntity;
import com.ssafy.invitationservice.invitation.adaptor.out.persistence.repository.InvitationRepository;
import com.ssafy.invitationservice.invitation.application.port.out.InvitationPort;
import com.ssafy.invitationservice.invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class InvitationPersistenceAdaptor implements InvitationPort {
    private final InvitationRepository invitationRepository;

    @Override
    public String saveInvitation(Invitation invitation) {
        String invitationCode = Invitation.createShortUUID();
        invitationRepository.save(InvitationEntity.of(invitation, invitationCode));
        return invitationCode;
    }

    @Override
    public Invitation viewInvitation(String invitationCode) {
        return Invitation.of(invitationRepository.findByInvitationCode(invitationCode));
    }

    @Override
    public List<Invitation> viewInvitationList(String userId) {
        List<Invitation>invitationList = new ArrayList<Invitation>();

        List<InvitationEntity>entityList = invitationRepository.findAllByUserID(userId, Integer.parseInt(userId));

        for (InvitationEntity entity : entityList) {
            invitationList.add(Invitation.of(entity));
        }

        return invitationList;
    }

    @Transactional
    @Override
    public Invitation modifyInvitation(Invitation invitation) {
        InvitationEntity entity = invitationRepository.findByInvitationCode(invitation.getInvitationCode());

        entity.changeInfo(invitation);

        return invitation;
    }

    @Transactional
    @Override
    public int deleteInvitation(String invitationCode) {
        
        //TODO 연관된 다른 데이터도 모두 삭제
        return invitationRepository.deleteByInvitationCode(invitationCode);
    }

    @Override
    public String setConfirmedDate(String invitationCode, String confirmedDate) {
        InvitationEntity entity = invitationRepository.findByInvitationCode(invitationCode);
        return entity.setConfirmedDate(confirmedDate);
    }
}
