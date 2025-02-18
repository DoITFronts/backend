package com.codeit.side.common.adapter.exception;

public class UserNotFoundException extends BusinessException {
    private static final String MESSAGE = "유저 정보가 없습니다.";

    public UserNotFoundException() {
        super(ErrorCode.NOT_FOUND, MESSAGE);
    }
}
