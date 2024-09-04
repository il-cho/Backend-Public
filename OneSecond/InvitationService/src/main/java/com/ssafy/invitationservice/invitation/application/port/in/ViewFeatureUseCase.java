package com.ssafy.invitationservice.invitation.application.port.in;

import com.ssafy.invitationservice.invitation.domain.Feature;

public interface ViewFeatureUseCase {
    Feature viewFeature(String invitationCode);
}
