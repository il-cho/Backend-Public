package com.ssafy.scheduleservice.schedule.application.port.in;

import com.ssafy.scheduleservice.schedule.domain.Schedule;

public interface ModifyScheduleUseCase {
    Schedule modifySchedule(String loginId, Schedule schedule);
}
