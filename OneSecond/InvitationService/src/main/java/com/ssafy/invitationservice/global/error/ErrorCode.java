package com.ssafy.invitationservice.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "00001", "서버 내부 에러"),
    /* Invitation */
    //TODO CRUD 해당하는 에러코드 다 추가해야 할까요?
    INVITATION_NOT_FOUND(BAD_REQUEST, "xxxx", "초대코드에 해당하는 초대장이 존재하지 않습니다."),

    /* Account */
    ACCOUNT_NOT_FOUND(BAD_REQUEST, "xxxx", "초대코드에 해당하는 계좌가 존재하지 않습니다."),

    /* Image */
    IMAGE_NOT_FOUND(NOT_FOUND, "", "이미지를 찾을 수 없습니다."),
    IMAGE_UPLOAD_ERROR(CONFLICT, "", "이미지 업로드 실패"),

    /* Participant */
    PARTICIPANT_NOT_FOUND(BAD_REQUEST, "", "참석자가 존재하지 않습니다."),

    /* Validation */
    INVALID_ARGUMENT(BAD_REQUEST, "xxxx", "입력값이 잘못됐습니다"),

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
