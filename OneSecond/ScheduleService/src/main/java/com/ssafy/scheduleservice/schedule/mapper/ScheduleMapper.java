package com.ssafy.scheduleservice.schedule.mapper;

import com.ssafy.scheduleservice.schedule.adaptor.in.web.dto.CreateScheduleInput;
import com.ssafy.scheduleservice.schedule.adaptor.in.web.dto.UpdateScheduleInput;
import com.ssafy.scheduleservice.schedule.adaptor.out.persistence.entity.ScheduleEntity;
import com.ssafy.scheduleservice.schedule.domain.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {
    public Schedule toSchedule(CreateScheduleInput createScheduleInput);
    public Schedule toSchedule(UpdateScheduleInput updateScheduleInput);
    public Schedule toSchedule(ScheduleEntity scheduleEntity);
    public ScheduleEntity toScheduleEntity(Schedule schedule);
    public CreateScheduleInput toCreateScheduleDto(Schedule schedule);
    public UpdateScheduleInput toUpdateScheduleDto(Schedule schedule);
}
