package com.ssafy.invitationservice.invitation.adaptor.out.persistence;

import com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity.ParticipantEntity;
import com.ssafy.invitationservice.invitation.adaptor.out.persistence.repository.ParticipantRepository;
import com.ssafy.invitationservice.invitation.application.mapper.ParticipantMapper;
import com.ssafy.invitationservice.invitation.application.port.out.ParticipantPort;
import com.ssafy.invitationservice.invitation.domain.Participant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ParticipantPersistenceAdaptor implements ParticipantPort {
    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    @Override
    public List<Participant> viewParticipantList(String invitationCode) {
        return participantMapper.toParticipantList(
                participantRepository.findAllByInvitationCode(invitationCode)
        );
    }

    @Override
    public Boolean viewParticipant(String invitationCode, int loginId) {
        return participantRepository.findByUserIdAndInvitationCode(loginId, invitationCode)
                .map(ParticipantEntity::isAttend)
                .orElseGet(() -> null);
    }

    @Override
    public Participant modifyAttend(Participant participant) {
        log.info("adaptor 초대장 코드 -> {}", participant.getInvitationCode());
        Optional<ParticipantEntity> entity = participantRepository.findByUserIdAndInvitationCode(participant.getUserId(), participant.getInvitationCode());
        if (entity.isEmpty()) { // null이면
            log.info("adaptor null 초대장 코드 -> {}", participant.getInvitationCode());
            ParticipantEntity participantEntity = participantMapper.toParticipantEntity(participant);
            log.info("adaptor null saved 초대장 코드 -> {}", participantEntity.getInvitationCode());
            ParticipantEntity saved = participantRepository.save(participantEntity);
            return participantMapper.toParticipant(saved);
        } else {
            log.info("adaptor not null 초대장 코드 -> {}", participant.getInvitationCode());
            entity.get().changeAttend(participant.isAttend());
            return participantMapper.toParticipant(entity.get());
        }
    }
}
