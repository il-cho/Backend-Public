package com.ssafy.scheduleservice.schedule.adaptor.out.persistence.repository;

import com.ssafy.scheduleservice.schedule.adaptor.out.persistence.entity.ScheduleEntity;
import com.ssafy.scheduleservice.schedule.domain.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ScheduleRepository extends MongoRepository<ScheduleEntity, String> {
    ScheduleEntity findByInvitationCode(String invitationCode);
    int deleteByInvitationCode(String invitationCode);
}
