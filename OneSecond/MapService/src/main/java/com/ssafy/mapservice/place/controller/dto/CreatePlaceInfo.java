package com.ssafy.mapservice.place.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePlaceInfo {

    private Long id;
    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

    @NotBlank
    private String placeType;

    @NotBlank
    private String placeName;

    @NotBlank
    private String placeAddress;
}
