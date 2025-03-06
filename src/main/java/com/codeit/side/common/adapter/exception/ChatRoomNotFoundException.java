package com.codeit.side.common.adapter.exception;

public class ChatRoomNotFoundException extends BusinessException {
    private static final String MESSAGE = "채팅방 정보가 없습니다.";

    public ChatRoomNotFoundException() {
        super(ErrorCode.NOT_FOUND, MESSAGE);
    }
}
