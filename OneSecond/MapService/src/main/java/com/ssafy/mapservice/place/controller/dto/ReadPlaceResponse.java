package com.ssafy.mapservice.place.controller.dto;

import com.ssafy.mapservice.place.enumeration.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ReadPlaceResponse {
    private String invitationCode;
    private String userId;
    private List<ReadPlaceInfo> placeInfo;

    public ReadPlaceResponse(String invitationCode, String userId) {
        this.invitationCode = invitationCode;
        this.userId = userId;
    }
}
