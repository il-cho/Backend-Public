package com.ssafy.scheduleservice.schedule.adaptor.out.persistence.entity;

import com.ssafy.scheduleservice.schedule.domain.Schedule;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "schedules")
@Getter
@Builder
@Slf4j
public class ScheduleEntity {
    @MongoId private String id;

    @Field("invitation_code")
    private String invitationCode;

    @Field("start_date")
    private String startDate;

    @Field("end_date")
    private String endDate;

    @Field("possible_date")
    private List<PossibleDate> possibleDate;

    @Field("is_confirmed")
    private Boolean confirmed;

    //TODO 예외처리
    public void changeInfo(String loginId, Schedule schedule){
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
        if(loginId ==null){
            this.possibleDate = schedule.getPossibleDate();
        }
        else{
            changePossibleDate(loginId, schedule);
        }

        this.confirmed = schedule.getConfirmed();
    }

    //TODO 예외처리
    public void changePossibleDate(String loginId, Schedule schedule){

        int userId = Integer.parseInt(loginId);
        List<String>dateList = null;

        for(PossibleDate newInfo: schedule.getPossibleDate()){
            if(newInfo.getUserId()==userId){
                dateList = newInfo.getDateList();
            }
        }

        if(dateList==null){
            return;
        }

        if(this.possibleDate == null){
            this.possibleDate = schedule.getPossibleDate();
        }
        else{
            //기존에 존재하던 유저
            for(PossibleDate info : this.possibleDate){
                if(info.getUserId()==userId){
                    info.changeDateList(dateList);
                    return ;
                }
            }

            //기존에 존재하지 않던 유저
            this.possibleDate.add(
                    PossibleDate.builder()
                            .userId(userId)
                            .dateList(dateList)
                            .build()
            );
        }

    }
}