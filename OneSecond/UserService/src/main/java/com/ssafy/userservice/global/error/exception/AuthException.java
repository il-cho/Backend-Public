package com.ssafy.userservice.global.error.exception;

import com.ssafy.userservice.global.error.ErrorCode;

public class AuthException extends AbstractErrorException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
