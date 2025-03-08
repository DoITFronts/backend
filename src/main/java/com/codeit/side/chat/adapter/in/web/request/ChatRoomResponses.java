package com.codeit.side.chat.adapter.in.web.request;

import com.codeit.side.chat.domain.ChatRoomInfo;

import java.util.List;

public record ChatRoomResponses(List<ChatRoomResponse> chatRooms) {
    public static ChatRoomResponses from(List<ChatRoomInfo> chatRoomInfos) {
        List<ChatRoomResponse> chatRoomResponses = chatRoomInfos.stream()
                .map(ChatRoomResponse::from)
                .toList();
        return new ChatRoomResponses(chatRoomResponses);
    }
}
