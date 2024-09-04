package com.ssafy.invitationservice.invitation.adaptor.out.persistence;

import com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity.FeatureEntity;
import com.ssafy.invitationservice.invitation.adaptor.out.persistence.repository.FeatureRepository;
import com.ssafy.invitationservice.invitation.application.mapper.FeatureMapper;
import com.ssafy.invitationservice.invitation.application.port.out.FeaturePort;
import com.ssafy.invitationservice.invitation.domain.Feature;
import com.ssafy.invitationservice.invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class FeaturePersistenceAdaptor implements FeaturePort {

    private final FeatureRepository featureRepository;
    private final FeatureMapper featureMapper;

    @Override
    public void saveFeature(Feature feature) {
        featureRepository.save(featureMapper.toFeatureEntity(feature));
    }

    @Override
    public Feature viewFeature(String invitationCode) {
        FeatureEntity entity = featureRepository.findByInvitationCode(invitationCode);
        return featureMapper.toFeature(entity);
    }

    @Transactional
    @Override
    public Feature modifyFeature(Feature feature) {

        FeatureEntity entity = featureRepository.findByInvitationCode(feature.getInvitationCode());

        entity.changeInfo(feature);

        return featureMapper.toFeature(entity);
    }

    @Override
    public int deleteFeature(String invitationCode) {
        return featureRepository.deleteByInvitationCode(invitationCode);
    }
}
