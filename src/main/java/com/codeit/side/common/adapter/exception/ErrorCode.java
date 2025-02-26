package com.codeit.side.common.adapter.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT_VALUE(40001, "잘못된 요청 파라미터입니다."),
    AUTHENTICATION_FAILED(40101, "로그인 유저가 아닙니다."),
    NOT_FOUND(40401, "없는 데이터 입니다."),
    INTERNAL_SERVER_ERROR(50001, "잘못된 요청 파라미터입니다."),
    ALREADY_JOINED(40901, "이미 참여한 번개입니다."),
    ALREADY_FULL(40902, "이미 참여인원이 꽉 찼습니다."),
    NOT_JOINED(40903, "참여한 번개가 아닙니다."),
    ;

    private final int code;
    private final String message;

    public int getStatusCode() {
        return code / 100;
    }
}
