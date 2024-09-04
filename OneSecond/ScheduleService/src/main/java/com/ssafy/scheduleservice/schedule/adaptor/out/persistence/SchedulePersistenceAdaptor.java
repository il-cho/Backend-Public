package com.ssafy.scheduleservice.schedule.adaptor.out.persistence;

import com.ssafy.scheduleservice.schedule.adaptor.out.persistence.entity.PossibleDate;
import com.ssafy.scheduleservice.schedule.adaptor.out.persistence.entity.ScheduleEntity;
import com.ssafy.scheduleservice.schedule.adaptor.out.persistence.repository.ScheduleRepository;
import com.ssafy.scheduleservice.schedule.application.port.out.SchedulePort;
import com.ssafy.scheduleservice.schedule.domain.Schedule;
import com.ssafy.scheduleservice.schedule.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class SchedulePersistenceAdaptor implements SchedulePort {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper mapper;

    @Transactional
    @Override
    public String saveSchedule(Schedule schedule) {
        ScheduleEntity entity = scheduleRepository.findByInvitationCode(schedule.getInvitationCode());
        if(entity == null){
            entity = mapper.toScheduleEntity(schedule);
            scheduleRepository.save(entity);
        }
        //이미 초대장이 존재하는 경우 새로 덮어씌움
        else{
             entity.changeInfo(null, schedule);
             scheduleRepository.save(entity);
        }

        return entity.getId();
    }

    @Override
    public Schedule viewSchedule(String invitationCode) {
        ScheduleEntity entity = scheduleRepository.findByInvitationCode(invitationCode);

        return mapper.toSchedule(entity);
    }

    @Transactional
    @Override
    public Schedule modifySchedule(String loginId, Schedule schedule) {
        ScheduleEntity entity = scheduleRepository.findByInvitationCode(schedule.getInvitationCode());

        entity.changeInfo(loginId, schedule);

        scheduleRepository.save(entity);
        return mapper.toSchedule(entity);
    }

    @Override
    public int deleteSchedule(String invitationCode) {
        return scheduleRepository.deleteByInvitationCode(invitationCode);
    }
}
