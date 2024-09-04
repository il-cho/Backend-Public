package com.ssafy.invitationservice.invitation.adaptor.out.persistence.repository;

import com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {
    Optional<ParticipantEntity> findByUserIdAndInvitationCode(int userId, String invitationCode);
    List<ParticipantEntity> findAllByInvitationCode(String invitationCode);
}
