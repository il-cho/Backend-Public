package com.ssafy.mapservice.place.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreatePlaceRequest {

    @NotBlank(message = "유저 ID를 입력해주세요.")
    private String userId;

    @NotBlank(message = "초대장 코드를 입력해주세요.")
    private String invitationCode;

    private List<CreatePlaceInfo> placeInfo;
}
