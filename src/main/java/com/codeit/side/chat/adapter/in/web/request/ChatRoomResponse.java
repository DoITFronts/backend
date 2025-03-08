package com.codeit.side.chat.adapter.in.web.request;

import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.ChatRoomInfo;

public record ChatRoomResponse(Long id, String name, String lastMessageContent, Integer memberCount) {
    public static ChatRoomResponse from(ChatRoomInfo chatRoomInfo) {
        ChatMessage lastMessage = chatRoomInfo.getLastMessage();
        return new ChatRoomResponse(
                chatRoomInfo.getChatRoom().getId(),
                chatRoomInfo.getChatRoom().getName(),
                lastMessage == null ? null : lastMessage.getContent(),
                chatRoomInfo.getMemberCount()
        );
    }
}
