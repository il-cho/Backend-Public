package com.ssafy.invitationservice.global.error.exception;

import com.ssafy.invitationservice.global.error.ErrorCode;

public class FileException extends AbstractBusinessLogicException {
    public FileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
