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
    private final LocalDateTime date;
    private final Long userId;
    private final String userNickname;
    private final ChatType type;
    private final String content;

    public static ChatMessage of(Long roomId, LocalDateTime date, Long userId, String userNickname, ChatType type, String content) {
        return new ChatMessage(null, roomId, date, userId, userNickname, type, content);
    }
}
