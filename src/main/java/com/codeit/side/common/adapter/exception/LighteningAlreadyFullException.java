package com.codeit.side.common.adapter.exception;

public class LighteningAlreadyFullException extends BusinessException {
    private static final String MESSAGE = "정원이 가득찬 번개입니다. lighteningId: %s";

    public LighteningAlreadyFullException(Long id) {
        super(ErrorCode.ALREADY_FULL, MESSAGE.formatted(id));
    }
}
