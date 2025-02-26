package com.codeit.side.common.adapter.exception;

public class UserAlreadyJoinedException extends BusinessException {
    public UserAlreadyJoinedException(String message) {
        super(ErrorCode.ALREADY_JOINED, message);
    }
}
