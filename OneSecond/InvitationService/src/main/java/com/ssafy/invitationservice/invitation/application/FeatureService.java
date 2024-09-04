package com.ssafy.invitationservice.invitation.application;

import com.ssafy.invitationservice.invitation.application.port.in.CreateFeatureUseCase;
import com.ssafy.invitationservice.invitation.application.port.in.DeleteFeatureUseCase;
import com.ssafy.invitationservice.invitation.application.port.in.ModifyFeatureUseCase;
import com.ssafy.invitationservice.invitation.application.port.in.ViewFeatureUseCase;
import com.ssafy.invitationservice.invitation.application.port.out.FeaturePort;
import com.ssafy.invitationservice.invitation.domain.Feature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeatureService implements CreateFeatureUseCase, ModifyFeatureUseCase, ViewFeatureUseCase, DeleteFeatureUseCase {

    private final FeaturePort featurePort;
    @Transactional
    @Override
    public void createFeature(String invitationCode) {
        Feature feature = Feature.builder()
                .place(false)
                .chat(false)
                .account(false)
                .chatbot(false)
                .schedule(false)
                .invitationCode(invitationCode)
                .build();
        featurePort.saveFeature(feature);
    }
    @Transactional
    @Override
    public int deleteFeature(String invitationCode) {
        return featurePort.deleteFeature(invitationCode);
    }
    @Transactional
    @Override
    public Feature modifyFeature(Feature feature) {

        return featurePort.modifyFeature(feature);
    }

    @Override
    public Feature viewFeature(String invitationCode) {
        return featurePort.viewFeature(invitationCode);
    }
}
