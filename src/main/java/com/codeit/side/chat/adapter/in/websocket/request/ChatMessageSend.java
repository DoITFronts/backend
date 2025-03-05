package com.codeit.side.chat.adapter.in.websocket.request;

import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.ChatType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageSend {
    private final Long id;
    private final Long roomId;
    private final LocalDateTime date;
    private final Long userId;
    private final String userNickname;
    private final ChatType type;
    private final String content;
    private final String image;

    public static ChatMessageSend of(ChatMessage chatMessage, String image) {
        return new ChatMessageSend(
                chatMessage.getId(),
                chatMessage.getRoomId(),
                chatMessage.getDate(),
                chatMessage.getUserId(),
                chatMessage.getUserNickname(),
                chatMessage.getType(),
                chatMessage.getContent(),
                image
        );
    }
}
