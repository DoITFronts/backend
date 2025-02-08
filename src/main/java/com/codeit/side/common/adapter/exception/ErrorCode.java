package com.codeit.side.common.adapter.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT_VALUE(40001, "잘못된 요청 파라미터입니다."),
    INTERNAL_SERVER_ERROR(50001, "잘못된 요청 파라미터입니다."),
    ;

    private final int code;
    private final String message;

    public int getStatusCode() {
        return code / 100;
    }
}
