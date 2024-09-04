package com.ssafy.userservice.global.error.exception;

import com.ssafy.userservice.global.error.ErrorCode;

public class UserException extends AbstractErrorException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
