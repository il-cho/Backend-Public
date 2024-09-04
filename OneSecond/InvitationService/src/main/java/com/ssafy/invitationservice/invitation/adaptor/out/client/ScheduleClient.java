package com.ssafy.invitationservice.invitation.adaptor.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "schedule-service")
public interface ScheduleClient {

    @DeleteMapping("/schedule/{invitationCode}")
    void deleteSchedule(@PathVariable("invitationCode") String invitationCode);
}
