package com.codeit.side.common.adapter.exception;

public class LighteningNotFoundException extends BusinessException {
    private static final String MESSAGE = "번개 정보가 없습니다. lighteningId: %s";

    public LighteningNotFoundException(Long id) {
        super(ErrorCode.NOT_FOUND, MESSAGE.formatted(id));
    }
}
