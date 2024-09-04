package com.ssafy.scheduleservice.schedule.adaptor.out.client;

import com.ssafy.scheduleservice.schedule.adaptor.out.client.dto.ConfirmedDateInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "invitation-service")
public interface InvitationClient {

    @PutMapping("/invitation/{invitationCode}/confirmed-date")
    String setConfirmedDate(@PathVariable("invitationCode") String invitationCode, @RequestBody ConfirmedDateInput input);
}
