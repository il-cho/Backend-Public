package com.ssafy.invitationservice.invitation.application.port.in;

import com.ssafy.invitationservice.invitation.domain.Feature;

public interface ModifyFeatureUseCase {
    Feature modifyFeature(Feature feature);
}
