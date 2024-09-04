package com.ssafy.userservice.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "00000", "서버 내부 에러"),

    /* Auth 관련 Request Error */
    IllegalArgument(BAD_REQUEST, "00001", "요청에 문제가 있습니다."),

    NeedLogin(NON_AUTHORITATIVE_INFORMATION, "00002", "로그인이 필요합니다."),

    NoUser(NO_CONTENT, "00003", "조회할 수 없거나 이미 삭제된 유저입니다."),

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
