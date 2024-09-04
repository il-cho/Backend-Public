package com.ssafy.userservice.global.error;

import com.ssafy.userservice.global.error.exception.AuthException;
import com.ssafy.userservice.global.error.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse("00001", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }
}
