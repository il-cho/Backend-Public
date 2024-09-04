package com.ssafy.mapservice.global.error;

import com.ssafy.mapservice.global.error.exception.PlaceException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.pqc.asn1.SPHINCSPLUSPublicKey;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.INVALID_ARGUMENT;
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(PlaceException.class)
    public ResponseEntity<ErrorResponse> handlePlaceException(PlaceException e) {
        log.error(e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.INVALID_PLACE_TYPE;
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
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
