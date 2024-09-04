package com.ssafy.invitationservice.invitation.adaptor.in.web;

import com.ssafy.invitationservice.global.error.ErrorCode;
import com.ssafy.invitationservice.global.error.exception.FileException;
import com.ssafy.invitationservice.invitation.adaptor.in.web.dto.*;
import com.ssafy.invitationservice.invitation.adaptor.out.client.ScheduleClient;
import com.ssafy.invitationservice.invitation.application.S3Service;
import com.ssafy.invitationservice.invitation.application.mapper.FeatureMapper;
import com.ssafy.invitationservice.invitation.application.port.in.*;
import com.ssafy.invitationservice.invitation.domain.Feature;
import com.ssafy.invitationservice.invitation.domain.Invitation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name="Invitation", description = "초대장 CRUD API")
@RequiredArgsConstructor
@RequestMapping("/invitation")
@Slf4j
public class InvitationController {
    private final CreateInvitationUseCase createInvitationUseCase;
    private final ViewInvitationUseCase viewInvitationUseCase;
    private final ViewInvitationListUseCase viewInvitationListUseCase;
    private final ModifyInvitationUseCase modifyInvitationUseCase;
    private final DeleteInvitationUseCase deleteInvitationUseCase;
    private final S3Service s3Service;
    private final ScheduleClient scheduleClient;
    private final SetConfirmedDateUseCase setConfirmedDateUseCase;

    private final CreateFeatureUseCase createFeatureUseCase;
    private final DeleteFeatureUseCase deleteFeatureUseCase;
    private final ModifyFeatureUseCase modifyFeatureUseCase;
    private final ViewFeatureUseCase viewFeatureUseCase;
    private final FeatureMapper featureMapper;

    //각 필드마다 적합한 contetn-type을 지정해줘야하는데 Swagger에서는 그것이 불가능 
    //생성의 경우 POSTMAN을 사용하거나 input field를 전부 분리해야함
    @PostMapping
    @Operation(summary = "초대장 생성 API", description = "이미지, 문구, 설명을 입력하여 초대장을 생성한다. (access token)")
    public ResponseEntity<?> createInvitation(
            @RequestPart("file") MultipartFile file,
            @RequestPart("input") CreateInvitationInput input,
            @RequestHeader("loginId") String loginId) {
        log.info("초대장 생성 API");
        log.info("초대장 생성자 -> {}", loginId);
        String invitationCode = createInvitationUseCase.createInvitation(input.toDomain());

        log.info("초대장 기능 설정 생성 API");
        log.info("Controller 초대장 코드 -> {}", invitationCode);
        createFeatureUseCase.createFeature(invitationCode);
        
        try {
            String url = s3Service.upload(file, invitationCode);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CreateInvitationOutput(url, invitationCode));
        } catch (IOException e) {
            throw new FileException(ErrorCode.IMAGE_UPLOAD_ERROR);
        }
    }

    @GetMapping("/view/{invitationCode}")
    @Operation(summary = "초대장 조회 API", description = "초대장 코드에 해당하는 초대장을 조회한다. (access token)")
    public ResponseEntity<?> viewInvitation(@PathVariable("invitationCode") String invitationCode) {
        log.info("초대장 조회 API");

        Invitation invitation = viewInvitationUseCase.viewInvitation(invitationCode);
        Feature feature = viewFeatureUseCase.viewFeature(invitationCode);

        Map<String, Object> map = new HashMap<>();
        map.put("info", invitation);
        map.put("feature", feature);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/list")
    @Operation(summary = "초대장 목록 조회 API", description = "사용자 ID에 해당하는 초대장 리스트를 조회한다. (access token)")
    public ResponseEntity<?> viewInvitationList(@RequestHeader("loginId") String loginId) {
        log.info("초대장 목록 조회 API");
        log.info ("로그인 ID -> {}",loginId);
        return ResponseEntity.ok(viewInvitationListUseCase.viewInvitationList(loginId));
    }

    @PutMapping
    @Operation(summary = "초대장 수정 API", description = "초대장 코드에 해당하는 초대장의 이미지, 문구, 설명을 수정한다. (access token)")
    public ResponseEntity<?> updateInvitation(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("input") UpdateInvitationDto input) {
        log.info("초대장 수정 API");
        try {
            if (file != null) {
                s3Service.upload(file, input.getInvitationCode());
            }
            return ResponseEntity.ok(modifyInvitationUseCase.modifyInvitation(input.toDomain()));
        } catch (Exception e) {
            throw new FileException(ErrorCode.IMAGE_UPLOAD_ERROR);
        }
    }

    @DeleteMapping("/{invitationCode}")
    @Operation(summary = "초대장 삭제 API", description = "생성자가 초대장 코드에 해당하는 초대장을 삭제한다. (access token)")
    public ResponseEntity<?> deleteInvitation(@PathVariable("invitationCode") String invitationCode) {
        log.info("초대장 삭제 API");
        try {
            s3Service.deleteFile(invitationCode);
            //TODO 관련 정보도 같이 삭제 - RabbitMQ or Kafka or feign-client
            scheduleClient.deleteSchedule(invitationCode);

            //Feature 제거
            deleteFeatureUseCase.deleteFeature(invitationCode);
            return ResponseEntity.ok(deleteInvitationUseCase.deleteInvitation(invitationCode));
        } catch (Exception e) {
            throw new FileException(ErrorCode.SERVER_ERROR);
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("에러 메시지");
        }
    }

    @Operation(summary = "초대장 기능 수정 API", description = "초대장 기능 수정 사항을 적용한다. (access token)")
    @PutMapping("/{invitationCode}/feature")
    public ResponseEntity<?> viewFeature(@PathVariable("invitationCode") String invitationCode,
                                         @RequestBody UpdateFeatureInput input) {
        log.info("초대장 기능 수정 API");
        log.info("초대장 코드 -> {}", invitationCode);
        return ResponseEntity.ok(modifyFeatureUseCase.modifyFeature(featureMapper.toFeature(input)));
    }

    @Operation(summary = "확정 일자 API", description = "초대장 확정 일자를 정한다.")
    @PutMapping("/{invitationCode}/confirmed-date")
    public ResponseEntity<?> setConfirmedDate(@PathVariable("invitationCode") String invitationCode,
                                              @RequestBody ConfirmedDateInput input) {
        log.info("확정 일자 API");
        return ResponseEntity.ok(setConfirmedDateUseCase.setConfirmedDate(invitationCode, input.getStartDate()));
    }
}
