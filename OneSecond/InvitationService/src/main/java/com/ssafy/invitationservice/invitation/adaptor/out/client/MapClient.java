package com.ssafy.invitationservice.invitation.adaptor.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "map-service")
public interface MapClient {

    @DeleteMapping("/places/invitation/{invitationCode}")
    void deletePlace(@PathVariable("invitationCode") String invitationCode);
}
