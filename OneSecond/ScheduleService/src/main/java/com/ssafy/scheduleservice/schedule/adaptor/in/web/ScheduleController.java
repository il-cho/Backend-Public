package com.ssafy.scheduleservice.schedule.adaptor.in.web;

import com.ssafy.scheduleservice.schedule.adaptor.in.web.dto.CreateScheduleInput;
import com.ssafy.scheduleservice.schedule.adaptor.in.web.dto.UpdateScheduleInput;
import com.ssafy.scheduleservice.schedule.application.port.in.CreateScheduleUseCase;
import com.ssafy.scheduleservice.schedule.application.port.in.DeleteScheduleUseCase;
import com.ssafy.scheduleservice.schedule.application.port.in.ModifyScheduleUseCase;
import com.ssafy.scheduleservice.schedule.application.port.in.ViewScheduleUseCase;
import com.ssafy.scheduleservice.schedule.mapper.ScheduleMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Tag(name="Schedule", description = "일정 CRUD API")
@Slf4j
public class ScheduleController {

    private final CreateScheduleUseCase createScheduleUseCase;
    private final ViewScheduleUseCase viewScheduleUseCase;
    private final ModifyScheduleUseCase modifyScheduleUseCase;
    private final DeleteScheduleUseCase deleteScheduleUseCase;
    private final ScheduleMapper mapper;

    @PostMapping
    @Operation(summary = "일정 생성", description = "초대장을 생성 한 일정을 바탕으로 일정을 추가한다..")
    public ResponseEntity<?> createSchedule(@RequestBody CreateScheduleInput createScheduleInput) {
        log.info("일정 생성 API");
        return ResponseEntity.status(HttpStatus.CREATED).body(createScheduleUseCase.createSchedule(mapper.toSchedule(createScheduleInput)));
    }

    @GetMapping("/{invitationCode}")
    @Operation(summary = "일정 조회", description = "초대장을 바탕으로 일정을 조회한다..")
    public ResponseEntity<?> getSchedule(@PathVariable("invitationCode") String invitationCode) {
        log.info("일정 조회 API");
        return ResponseEntity.status(HttpStatus.OK).body(viewScheduleUseCase.viewSchedule(invitationCode));
    }

    @PutMapping
    @Operation(summary = "일정 수정", description = "일정을 수정한다.")
    public ResponseEntity<?> modifySchedule(@RequestHeader("loginId") String loginId, @RequestBody UpdateScheduleInput updateScheduleInput) {

        log.info("일정 수정 API");
        return ResponseEntity.status(HttpStatus.OK).body(modifyScheduleUseCase.modifySchedule(loginId,
                mapper.toSchedule(updateScheduleInput)
        ));
    }

    @DeleteMapping("/{invitationCode}")
    @Operation(summary = "일정 삭제", description = "일정을 삭제한다.")
    public ResponseEntity<?> deleteSchedule(@PathVariable("invitationCode") String invitationCode) {
        log.info("일정 삭제 API");
        return ResponseEntity.status(HttpStatus.OK).body(deleteScheduleUseCase.deleteSchedule(invitationCode));
    }
}
