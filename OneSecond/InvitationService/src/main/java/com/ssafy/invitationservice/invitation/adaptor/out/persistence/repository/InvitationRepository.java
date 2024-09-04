package com.ssafy.invitationservice.invitation.adaptor.out.persistence.repository;

import com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity.InvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvitationRepository extends JpaRepository<InvitationEntity, Long> {
    InvitationEntity findByInvitationCode(String invitationCode);
    @Query(
            "SELECT i FROM InvitationEntity i LEFT JOIN  ParticipantEntity p ON  i.invitationCode = p.invitationCode " +
                    "WHERE (i.confirmedDate IS NOT NULL ) AND (i.inviter = :inviter OR p.userId = :userId) " +
                    "ORDER BY i.confirmedDate")
    List<InvitationEntity> findAllByUserID(@Param("inviter")String inviter, @Param("userId") int userId);
    int deleteByInvitationCode(String invitationCode);
}
