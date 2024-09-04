package com.ssafy.scheduleservice.schedule.adaptor.in.web.dto;

import com.ssafy.scheduleservice.schedule.adaptor.out.persistence.entity.PossibleDate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class UpdateScheduleInput {
    @NotBlank(message = "초대장 코드를 입력해주세요.")
    private String invitationCode;

    private String startDate;
    private String endDate;

    private List<PossibleDate> possibleDate;
    private Boolean confirmed;
}
