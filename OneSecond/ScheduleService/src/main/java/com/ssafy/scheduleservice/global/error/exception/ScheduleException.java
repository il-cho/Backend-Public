package com.ssafy.scheduleservice.global.error.exception;

import com.ssafy.scheduleservice.global.error.ErrorCode;

public class ScheduleException extends AbstractBusinessLogicException{
    public ScheduleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
