package com.ssafy.scheduleservice.global.error.exception;

import com.ssafy.scheduleservice.global.error.ErrorCode;

public abstract class AbstractBusinessLogicException extends AbstractErrorException {
    public AbstractBusinessLogicException(ErrorCode errorCode) {
        super(errorCode);
    }
}
