package com.ssafy.mapservice.place.repository;

import com.ssafy.mapservice.place.entity.InvitationInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationInfoRepository extends JpaRepository<InvitationInfoEntity, Long> {
    Optional<InvitationInfoEntity> findUserByUserIdAndInvitationCode(String userId, String invitationCode);

    List<InvitationInfoEntity> findAllByInvitationCode(String invitationCode);

}
