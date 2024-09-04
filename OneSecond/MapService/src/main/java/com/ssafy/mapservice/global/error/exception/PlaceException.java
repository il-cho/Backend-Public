package com.ssafy.mapservice.global.error.exception;

import com.ssafy.mapservice.global.error.ErrorCode;

public class PlaceException extends AbstractErrorException {
    public PlaceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
