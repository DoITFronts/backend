package com.codeit.side.chat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomInfo {
    private final ChatRoom chatRoom;
    private final ChatMessage lastMessage;
    private final Integer memberCount;

    public static ChatRoomInfo of(ChatRoom chatRoom, ChatMessage lastMessage, Integer memberCount) {
        return new ChatRoomInfo(chatRoom, lastMessage, memberCount);
    }
}
