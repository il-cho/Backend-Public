package com.ssafy.chatservice.global.error;

public class InvalidChatRequestException extends RuntimeException {
    public InvalidChatRequestException(String message) {
        super(message);
    }
}
