package com.ssafy.invitationservice.invitation.application.mapper;

import com.ssafy.invitationservice.invitation.adaptor.out.persistence.entity.ParticipantEntity;
import com.ssafy.invitationservice.invitation.domain.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParticipantMapper {
    @Mapping(source = "invitationCode", target = "invitationCode")
    Participant toParticipant(ParticipantEntity participantEntity);

    @Mapping(source = "invitationCode", target = "invitationCode")
    ParticipantEntity toParticipantEntity(Participant participant);

    List<Participant> toParticipantList(List<ParticipantEntity> participantEntityList);
}
