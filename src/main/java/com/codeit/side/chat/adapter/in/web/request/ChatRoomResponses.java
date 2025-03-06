package com.codeit.side.chat.adapter.in.web.request;

import com.codeit.side.chat.domain.ChatRoom;

import java.util.List;

public record ChatRoomResponses() {
    public static ChatRoomResponses from(List<ChatRoom> chatRooms) {
        return new ChatRoomResponses();
    }
}
