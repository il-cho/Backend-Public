package com.ssafy.mapservice.place.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ssafy.mapservice.global.error.ErrorCode;
import com.ssafy.mapservice.global.error.exception.PlaceException;
import org.springframework.http.converter.HttpMessageNotReadableException;

public enum PlaceType {
    CANDIDATE, START, CONFIRM;

    @JsonCreator
    public static PlaceType fromString(String string) {
        return PlaceType.valueOf(string.toUpperCase());
    }
}
