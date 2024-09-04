package com.ssafy.mapservice.place.repository;

import com.ssafy.mapservice.place.entity.PlaceEntity;
import com.ssafy.mapservice.place.enumeration.PlaceType;

import java.util.List;

public interface PlaceRepositoryCustom {
    List<PlaceEntity> getPlacesByCond(String invitationCode, String userId, PlaceType placeType);
}
