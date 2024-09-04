package com.ssafy.mapservice.place.mapper;

import com.ssafy.mapservice.place.controller.dto.*;
import com.ssafy.mapservice.place.entity.InvitationInfoEntity;
import com.ssafy.mapservice.place.entity.PlaceEntity;
import com.ssafy.mapservice.place.enumeration.PlaceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomPlaceMapperImpl implements CustomPlaceMapper{

    private final PlaceMapper placeMapper;

    /**
     *
     * @from PlaceEntity
     * @to List of ReadPlaceResponse
     * @description
     *  mapping function that convert from entity to response object for communication
     */
    public List<ReadPlaceResponse> toListReadPlaceResponse(List<PlaceEntity> findPlaces) {
        List<ReadPlaceResponse> readPlaceResponses = findPlaces
                .stream()
                .map(placeEntity -> new ReadPlaceResponse(
                        placeEntity.getInvitationInfoEntity().getInvitationCode(),
                        placeEntity.getInvitationInfoEntity().getUserId()
                ))
                .distinct()
                .toList();
        readPlaceResponses
                .forEach(readPlaceResponse -> readPlaceResponse
                        .setPlaceInfo(getPlaceList(findPlaces, readPlaceResponse)));
        return readPlaceResponses;

    }

    /**
     *
     * @from List of placeEntity
     * @to List of ReadPlaceResponse
     * @description
     *  mapping function that convert from entity to response object for communication
     */
    @Override
    public CreatePlaceResponse toCreateResponse(List<PlaceEntity> savedPlaceEntities) {
        List<CreatePlaceInfo> placeInfoList = savedPlaceEntities
                .stream()
                .map((placeEntity
                        -> new CreatePlaceInfo(
                        placeEntity.getId(),
                        placeEntity.getLongitude(),
                        placeEntity.getLatitude(),
                        placeEntity.getPlaceType().toString(),
                        placeEntity.getPlaceName(),
                        placeEntity.getPlaceAddress()
                )))
                .toList();

        return new CreatePlaceResponse(
                    extractUserIdFromPlaceEntity(savedPlaceEntities),
                    extractInvitationCodeFromPlaceEntity(savedPlaceEntities),
                    placeInfoList
                );
    }
    @Override
    public String extractUserIdFromPlaceEntity(List<PlaceEntity> savedPlaceEntities) {
        return savedPlaceEntities.get(0).getInvitationInfoEntity().getUserId();
    }

    @Override
    public String extractInvitationCodeFromPlaceEntity(List<PlaceEntity> savedPlaceEntities) {
        return savedPlaceEntities.get(0).getInvitationInfoEntity().getInvitationCode();
    }

    @Override
    public InvitationInfoEntity extractUserEntityFromRequest(CreatePlaceRequest createPlaceRequest) {
        return new InvitationInfoEntity(createPlaceRequest.getUserId(), createPlaceRequest.getInvitationCode());
    }


    @Override
    public List<PlaceEntity> extractPlaceEntityFromRequest(CreatePlaceRequest createPlaceRequest) {
        return createPlaceRequest.getPlaceInfo()
                .stream()
                .map(placeMapper::toPlaceEntity)
                .toList();
    }

    @Override
    public PlaceType toPlaceTypeEnumFromString(String placeType) {
        return (placeType != null) ? PlaceType.valueOf(placeType.toUpperCase())  : null;
    }

    private List<ReadPlaceInfo> getPlaceList(List<PlaceEntity> findPlaces, ReadPlaceResponse readPlaceResponse) {
        return findPlaces
                .stream()
                .filter(placeEntity -> userIdAndInvitationCodeEqualTest(placeEntity, readPlaceResponse))
                .map(placeEntity -> new ReadPlaceInfo(
                        placeEntity.getId(),
                        placeEntity.getLongitude(),
                        placeEntity.getLongitude(),
                        placeEntity.getPlaceType().toString(),
                        placeEntity.getPlaceName(),
                        placeEntity.getPlaceAddress()
                ))
                .toList();
    }

    private boolean userIdAndInvitationCodeEqualTest(PlaceEntity placeEntity, ReadPlaceResponse readPlaceResponse) {
        return placeEntity.getInvitationInfoEntity().getInvitationCode() == readPlaceResponse.getInvitationCode()
                && placeEntity.getInvitationInfoEntity().getUserId() == readPlaceResponse.getUserId();
    }


}
