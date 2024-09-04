package com.ssafy.scheduleservice.schedule.application.port.out;

import com.ssafy.scheduleservice.schedule.domain.Schedule;

public interface SchedulePort {
    String saveSchedule(Schedule schedule);
    Schedule viewSchedule(String invitationCode);
    Schedule modifySchedule(String loginId, Schedule schedule);
    int deleteSchedule(String invitationCode);
}
