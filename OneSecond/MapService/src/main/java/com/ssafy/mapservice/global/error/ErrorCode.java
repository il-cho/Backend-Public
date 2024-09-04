package com.ssafy.mapservice.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "00001", "서버 내부 에러"),
    /* Validation */
    INVALID_ARGUMENT(BAD_REQUEST, "xxxx", "입력값이 잘못됐습니다"),

    INVALID_PLACE_TYPE(BAD_REQUEST, "xxxx", "장소 타입은 출발지, 후보지, 확정 중 하나여야합니다." )
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
