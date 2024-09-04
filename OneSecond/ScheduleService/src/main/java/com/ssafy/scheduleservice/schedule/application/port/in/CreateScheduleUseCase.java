package com.ssafy.scheduleservice.schedule.application.port.in;

import com.ssafy.scheduleservice.schedule.domain.Schedule;

public interface CreateScheduleUseCase {
    String createSchedule(Schedule schedule);
}
