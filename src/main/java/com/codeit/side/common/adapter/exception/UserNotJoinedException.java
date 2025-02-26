package com.codeit.side.common.adapter.exception;

public class UserNotJoinedException extends BusinessException {
    private static final String MESSAGE = "참여하지 않은 번개입니다. id: %s";

    public UserNotJoinedException(Long id) {
        super(ErrorCode.NOT_JOINED, MESSAGE.formatted(id));
    }
}
