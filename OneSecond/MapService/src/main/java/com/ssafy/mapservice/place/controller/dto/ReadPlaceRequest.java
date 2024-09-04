package com.ssafy.mapservice.place.controller.dto;

import com.ssafy.mapservice.place.enumeration.PlaceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ReadPlaceRequest {
    @NotBlank(message = "초대장 코드를 입력해주세요.")
    private String invitationCode;
    private String userId;
    private String placeType;
}
