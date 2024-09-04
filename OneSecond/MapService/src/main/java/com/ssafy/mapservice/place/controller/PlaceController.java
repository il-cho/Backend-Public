package com.ssafy.mapservice.place.controller;
import com.ssafy.mapservice.global.error.ErrorCode;
import com.ssafy.mapservice.global.error.exception.PlaceException;
import com.ssafy.mapservice.place.controller.dto.*;
import com.ssafy.mapservice.place.entity.InvitationInfoEntity;
import com.ssafy.mapservice.place.entity.PlaceEntity;
import com.ssafy.mapservice.place.enumeration.PlaceType;
import com.ssafy.mapservice.place.mapper.CustomPlaceMapper;
import com.ssafy.mapservice.place.mapper.PlaceMapper;
import com.ssafy.mapservice.place.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Tag(name="Place", description = "장소 CRD API")
@RequiredArgsConstructor
@RequestMapping("/places")
@Slf4j
@CrossOrigin("*")
public class PlaceController {
    private final PlaceService placeService;
    private final CustomPlaceMapper customPlaceMapper;

    private final String[] allowedEnumValues = {"candidate", "start", "confirm"};

    @Operation(summary = "장소 정보 추가 API", description = "유저 ID, 초대장 코드, 경도, 위도, 위치 타입을 받아 장소 정보를 저장한다. (access token)")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePlaceResponse createPlace(
            @RequestBody @Valid CreatePlaceRequest createPlaceRequest,
            @RequestParam(defaultValue = "false") Boolean refresh) {
        System.out.println("refresh: " +refresh);
        if(refresh) deleteAllInfoByInvitationCode(createPlaceRequest.getInvitationCode());
        validateCreatePlaceType(createPlaceRequest.getPlaceInfo());
        InvitationInfoEntity invitationInfoEntity = customPlaceMapper.extractUserEntityFromRequest(createPlaceRequest);
        List<PlaceEntity> placeEntityList = customPlaceMapper.extractPlaceEntityFromRequest(createPlaceRequest);
        List<PlaceEntity> savedPlaceEntities = placeService.savePlaceWithUserInfo(invitationInfoEntity, placeEntityList);
        return customPlaceMapper.toCreateResponse(savedPlaceEntities);
    }
    @Operation(summary = "장소 정보 삭제 API", description = "장소 ID를 기반으로 장소 정보를 삭제한다. (access token)")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeletePlaceResponse deletePlace(@PathVariable("id") Long id) {
        return new DeletePlaceResponse(placeService.deletePlaceById(id));
    }
    @Operation(summary = "장소 정보 조회 API", description = "초대장 코드. 유저 ID, 위치 타입을 받아 장소 정보를 조회한다.")
    @GetMapping
    public List<ReadPlaceResponse> getAllPlaces(@ModelAttribute @Valid ReadPlaceRequest readPlaceRequest) {
        validateReadPlaceType(readPlaceRequest.getPlaceType());
        List<PlaceEntity> findPlaces = placeService.getAllPlaces(
                readPlaceRequest.getInvitationCode(),
                readPlaceRequest.getUserId(),
                customPlaceMapper.toPlaceTypeEnumFromString(readPlaceRequest.getPlaceType())
            );
        return customPlaceMapper.toListReadPlaceResponse(findPlaces);
    }


    @Operation(summary = "초대장 삭제 API", description = "초대장 코드를 받아 초대장 코드와 관련된 모든 정보를 삭제한다.")
    @DeleteMapping("/invitation/{invitationCode}")
    public DeleteInvitationResponse deleteAllInfoByInvitationCode(@PathVariable("invitationCode") String invitationCode) {
        placeService.deleteByInvitationCode(invitationCode);
        return new DeleteInvitationResponse(true);
    }

    private void validateReadPlaceType(String placeType) {
        if(placeType != null)  isInAllowedValue(placeType);
    }

    private void validateCreatePlaceType(List<CreatePlaceInfo> placeInfo) {
        placeInfo.forEach(
                createPlaceInfo -> isInAllowedValue(createPlaceInfo.getPlaceType())
        );
    }

    private void isInAllowedValue(String placeType) {
        Arrays.stream(allowedEnumValues)
        .filter((allowedValue) -> allowedValue.equals(placeType))
        .findAny()
        .orElseThrow(() -> new PlaceException(ErrorCode.INVALID_PLACE_TYPE));
    }

}