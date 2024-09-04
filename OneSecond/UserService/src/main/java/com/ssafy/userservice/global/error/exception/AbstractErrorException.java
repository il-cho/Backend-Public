package com.ssafy.userservice.global.error.exception;

import com.ssafy.userservice.global.error.ErrorCode;
import lombok.Getter;

@Getter
public abstract class AbstractErrorException extends RuntimeException {
    private final ErrorCode errorCode;

    public AbstractErrorException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
