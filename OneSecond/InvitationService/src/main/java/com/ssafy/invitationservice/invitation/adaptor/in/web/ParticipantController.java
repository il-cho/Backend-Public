package com.ssafy.invitationservice.invitation.adaptor.in.web;

import com.ssafy.invitationservice.invitation.adaptor.in.web.dto.AttendInput;
import com.ssafy.invitationservice.invitation.application.port.in.AttendUseCase;
import com.ssafy.invitationservice.invitation.application.port.in.ViewParticipantListUseCase;
import com.ssafy.invitationservice.invitation.application.port.in.ViewParticipantUseCase;
import com.ssafy.invitationservice.invitation.domain.Participant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Participant", description = "참여자 API")
@RequiredArgsConstructor
@RequestMapping("/invitation/participant")
@Slf4j
public class ParticipantController {
    private final ViewParticipantListUseCase viewParticipantListUseCase;
    private final ViewParticipantUseCase viewParticipantUseCase;
    private final AttendUseCase attendUseCase;

    @GetMapping("/list/{invitationCode}")
    @Operation(summary = "참여자 목록 조회 API", description = "초대장 코드에 해당하는 모임에 참여하는 참여자 리스트를 조회한다. (access token)")
    public ResponseEntity<?> viewParticipantList(@PathVariable("invitationCode") String invitationCode) {
        log.info("참여자 목록 조회 API");
        List<Participant> participants = viewParticipantListUseCase.viewParticipantList(invitationCode);
        return ResponseEntity.ok(viewParticipantListUseCase.viewParticipantOutputList(participants));
    }

    @GetMapping("/{invitationCode}")
    @Operation(summary = "참석 여부 조회 API", description = "로그인한 유저가 모임에 참여하는지 조회한다. (access token)")
    public ResponseEntity<?> getAttend(@PathVariable("invitationCode") String invitationCode,
                                       @RequestHeader("loginId") String loginId) {
        log.info("참석 여부 조회 API");
        return ResponseEntity.ok(viewParticipantUseCase.viewParticipantUseCase(invitationCode, Integer.parseInt(loginId)));
    }


    @PostMapping("/{invitationCode}")
    @Operation(summary = "참석/불참 표시 API", description = "참석/불참을 표시한다. (access token)")
    public ResponseEntity<?> attendInvitation(@PathVariable("invitationCode") String invitationCode,
                                              @RequestBody AttendInput input,
                                              @RequestHeader("loginId") String loginId) {
        log.info("참석/불참 표시 API");
        log.info("초대장 코드 -> {}", invitationCode);
        return ResponseEntity.ok(attendUseCase.attend(Integer.parseInt(loginId), invitationCode, input.isAttend()));
    }
}
