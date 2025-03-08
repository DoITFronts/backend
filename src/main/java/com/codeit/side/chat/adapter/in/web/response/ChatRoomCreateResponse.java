package com.codeit.side.chat.adapter.in.web.response;

public record ChatRoomCreateResponse(Long id) {
    public static ChatRoomCreateResponse from(Long id) {
        return new ChatRoomCreateResponse(id);
    }
}
