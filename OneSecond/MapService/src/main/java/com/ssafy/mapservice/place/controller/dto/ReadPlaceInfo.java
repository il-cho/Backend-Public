package com.ssafy.mapservice.place.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ReadPlaceInfo {
    private Long id;
    private Double longitude;
    private Double latitude;
    private String placeType;
    private String placeName;
    private String placeAddress;
}
