package com.ssafy.mapservice.place.mapper;

import com.ssafy.mapservice.place.controller.dto.CreatePlaceInfo;
import com.ssafy.mapservice.place.controller.dto.ReadPlaceResponse;
import com.ssafy.mapservice.place.entity.PlaceEntity;
import com.ssafy.mapservice.place.enumeration.PlaceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaceMapper {

    @Mapping(source = "placeType", target = "placeType")
    PlaceEntity toPlaceEntity(CreatePlaceInfo createPlaceInfo);

    default PlaceType stringToPlaceType(String placeType) {
        if (placeType == null) {
            return null;
        }
        return PlaceType.valueOf(placeType.toUpperCase());
    }


}
