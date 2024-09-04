package com.ssafy.scheduleservice.global.error.exception;

import com.ssafy.scheduleservice.global.error.ErrorCode;

public class FileException extends AbstractBusinessLogicException {
    public FileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
