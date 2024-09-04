package com.ssafy.scheduleservice.global.error.exception;

import com.ssafy.scheduleservice.global.error.ErrorCode;

public class AccountException extends AbstractBusinessLogicException {
    public AccountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
