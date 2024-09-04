package com.ssafy.invitationservice.global.error.exception;

import com.ssafy.invitationservice.global.error.ErrorCode;

public abstract class AbstractBusinessLogicException extends AbstractErrorException {
    public AbstractBusinessLogicException(ErrorCode errorCode) {
        super(errorCode);
    }
}
