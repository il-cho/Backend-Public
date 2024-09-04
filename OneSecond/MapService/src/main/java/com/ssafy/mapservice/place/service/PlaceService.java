package com.ssafy.mapservice.place.service;

import com.ssafy.mapservice.place.entity.InvitationInfoEntity;
import com.ssafy.mapservice.place.entity.PlaceEntity;
import com.ssafy.mapservice.place.enumeration.PlaceType;

import java.util.List;

public interface PlaceService {

    Long deletePlaceById(Long id);

    List<PlaceEntity> getAllPlaces(String invitationCode, String userId, PlaceType placeType);

    List<PlaceEntity> savePlaceWithUserInfo(InvitationInfoEntity invitationInfoEntity, List<PlaceEntity> placeEntityList);

    void deleteByInvitationCode(String invitationCode);
}
