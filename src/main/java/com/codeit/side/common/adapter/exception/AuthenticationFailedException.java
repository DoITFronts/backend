package com.codeit.side.common.adapter.exception;

public class AuthenticationFailedException extends BusinessException {
    public AuthenticationFailedException(String message) {
        super(ErrorCode.AUTHENTICATION_FAILED, message);
    }
}
