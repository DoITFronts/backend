package com.codeit.side.common.adapter.exception;

public class EmailAlreadyExistsException extends BusinessException {
    public EmailAlreadyExistsException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
