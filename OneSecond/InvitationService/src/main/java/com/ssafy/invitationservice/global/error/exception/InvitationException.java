package com.ssafy.invitationservice.global.error.exception;

import com.ssafy.invitationservice.global.error.ErrorCode;

public class InvitationException extends AbstractBusinessLogicException {
    public InvitationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
