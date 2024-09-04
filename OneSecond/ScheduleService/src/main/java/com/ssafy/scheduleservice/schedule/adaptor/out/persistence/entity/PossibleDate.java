package com.ssafy.scheduleservice.schedule.adaptor.out.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Builder
public class PossibleDate {
    @Field("user_id")
    private int userId;

    @Field("date_list")
    private List<String> dateList;

    public void changeDateList(List<String> dateList) {
        this.dateList = dateList;
    }

}