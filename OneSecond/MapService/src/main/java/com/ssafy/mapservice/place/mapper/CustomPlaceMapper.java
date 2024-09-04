package com.ssafy.mapservice.place.mapper;

import com.ssafy.mapservice.place.controller.dto.CreatePlaceRequest;
import com.ssafy.mapservice.place.controller.dto.CreatePlaceResponse;
import com.ssafy.mapservice.place.controller.dto.ReadPlaceResponse;
import com.ssafy.mapservice.place.entity.InvitationInfoEntity;
import com.ssafy.mapservice.place.entity.PlaceEntity;
import com.ssafy.mapservice.place.enumeration.PlaceType;

import java.util.List;


/**
 * custom mapper to handle complex mapping logic
 */

public interface CustomPlaceMapper {
    List<ReadPlaceResponse> toListReadPlaceResponse(List<PlaceEntity> findPlaces);
    CreatePlaceResponse toCreateResponse(List<PlaceEntity> savedPlaceEntities);
    String extractUserIdFromPlaceEntity(List<PlaceEntity> savedPlaceEntities);
    String extractInvitationCodeFromPlaceEntity(List<PlaceEntity> savedPlaceEntities);
    InvitationInfoEntity extractUserEntityFromRequest(CreatePlaceRequest createPlaceRequest);
    List<PlaceEntity> extractPlaceEntityFromRequest(CreatePlaceRequest createPlaceRequest);

    PlaceType toPlaceTypeEnumFromString(String placeType);
}
