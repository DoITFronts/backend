package com.codeit.side.common.adapter.exception;

public class BusinessException extends RuntimeException {
    public ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
