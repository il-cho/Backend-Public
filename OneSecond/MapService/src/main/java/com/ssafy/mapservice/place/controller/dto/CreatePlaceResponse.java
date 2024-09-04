package com.ssafy.mapservice.place.controller.dto;

import com.ssafy.mapservice.place.enumeration.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreatePlaceResponse {
    private String userId;
    private String invitationCode;
    private List<CreatePlaceInfo> placeInfo;
}
