package com.ssafy.invitationservice.global.error.exception;

import com.ssafy.invitationservice.global.error.ErrorCode;

public class AccountException extends AbstractBusinessLogicException {
    public AccountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
