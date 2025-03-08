package com.codeit.side.chat.adapter.in.web.response;

import com.codeit.side.chat.domain.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        Long id,
        Long roomId,
        Long userId,
        String userNickname,
        String content,
        LocalDateTime createdAt
) {
    public static ChatMessageResponse from(ChatMessage chatMessage) {
        return new ChatMessageResponse(
                chatMessage.getId(),
                chatMessage.getRoomId(),
                chatMessage.getUserId(),
                chatMessage.getUserNickname(),
                chatMessage.getContent(),
                chatMessage.getCreatedAt()
        );
    }
}
