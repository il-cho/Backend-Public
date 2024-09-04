package com.ssafy.invitationservice.invitation.application.mapper;

import com.ssafy.invitationservice.invitation.adaptor.in.web.dto.UpdateFeatureInput;
import com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity.FeatureEntity;
import com.ssafy.invitationservice.invitation.domain.Feature;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeatureMapper {

    Feature toFeature(FeatureEntity featureEntity);
    Feature toFeature(UpdateFeatureInput updateFeatureInput);
    FeatureEntity toFeatureEntity(Feature feature);
}
