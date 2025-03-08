package com.codeit.side.chat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessage {
    private final Long id;
    private final Long roomId;
    private final LocalDateTime createdAt;
    private final Long userId;
    private final String userNickname;
    private final ChatType type;
    private final String content;

    public static ChatMessage of(Long roomId, LocalDateTime createdAt, Long userId, String userNickname, ChatType type, String content) {
        return of(null, roomId, createdAt, userId, userNickname, type, content);
    }

    public static ChatMessage of(Long id, Long roomId, LocalDateTime createdAt, Long userId, String userNickname, ChatType type, String content) {
        return new ChatMessage(id, roomId, createdAt, userId, userNickname, type, content);
    }
}
