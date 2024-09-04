package com.ssafy.invitationservice.invitation.application.port.out;

import com.ssafy.invitationservice.invitation.domain.Feature;

public interface FeaturePort {
    void saveFeature (Feature feature);
    Feature viewFeature(String invitationCode);
    Feature modifyFeature(Feature feature);
    int deleteFeature(String invitationCode);
}
