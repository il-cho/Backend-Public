package com.ssafy.scheduleservice.schedule.domain;

import com.ssafy.scheduleservice.schedule.adaptor.out.persistence.entity.PossibleDate;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Schedule {
    private String invitationCode;
    private String startDate;
    private String endDate;
    private List<PossibleDate> possibleDate;
    private Boolean confirmed;
}
