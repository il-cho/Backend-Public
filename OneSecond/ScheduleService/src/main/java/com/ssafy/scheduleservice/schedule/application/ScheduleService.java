package com.ssafy.scheduleservice.schedule.application;

import com.ssafy.scheduleservice.schedule.adaptor.out.client.InvitationClient;
import com.ssafy.scheduleservice.schedule.adaptor.out.client.dto.ConfirmedDateInput;
import com.ssafy.scheduleservice.schedule.application.port.in.CreateScheduleUseCase;
import com.ssafy.scheduleservice.schedule.application.port.in.DeleteScheduleUseCase;
import com.ssafy.scheduleservice.schedule.application.port.in.ModifyScheduleUseCase;
import com.ssafy.scheduleservice.schedule.application.port.in.ViewScheduleUseCase;
import com.ssafy.scheduleservice.schedule.application.port.out.SchedulePort;
import com.ssafy.scheduleservice.schedule.domain.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ScheduleService implements CreateScheduleUseCase, DeleteScheduleUseCase, ModifyScheduleUseCase, ViewScheduleUseCase {

    private final SchedulePort schedulePort;
    private final InvitationClient invitationClient;

    @Override
    public String createSchedule(Schedule schedule) {
        invitationClient.setConfirmedDate(schedule.getInvitationCode(), new ConfirmedDateInput(schedule.getStartDate().toString().substring(0, 10)));
//        if (schedule.getConfirmed()) {
//
//        }
        return schedulePort.saveSchedule(schedule);
    }

    @Override
    public int deleteSchedule(String invitationCode) {
        return schedulePort.deleteSchedule(invitationCode);
    }

    @Override
    public Schedule modifySchedule(String loginId, Schedule schedule) {
        invitationClient.setConfirmedDate(schedule.getInvitationCode(), new ConfirmedDateInput(schedule.getStartDate().toString().substring(0, 10)));
//        if (schedule.getConfirmed()) {
//
//        }
        return schedulePort.modifySchedule(loginId,schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public Schedule viewSchedule(String invitationCode) {
        return schedulePort.viewSchedule(invitationCode);
    }
}
