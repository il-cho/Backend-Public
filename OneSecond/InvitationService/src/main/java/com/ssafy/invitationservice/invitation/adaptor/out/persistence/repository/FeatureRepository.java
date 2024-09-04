package com.ssafy.invitationservice.invitation.adaptor.out.persistence.repository;

import com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity.FeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<FeatureEntity,Long> {
    FeatureEntity findByInvitationCode(String invitationCode);
    int deleteByInvitationCode (String invitationCode);
}
