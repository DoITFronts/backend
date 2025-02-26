package com.codeit.side.common.adapter.exception;

public class IllegalRequestException extends BusinessException {
    public IllegalRequestException(String message) {
        super(ErrorCode.INVALID_INPUT_VALUE, message);
    }
}
