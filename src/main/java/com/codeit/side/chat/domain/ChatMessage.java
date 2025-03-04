package com.codeit.side.chat.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessage {
    private Long id;
    private Long bookClubId;
    private LocalDateTime date;
    private Long userId;
    private String userNickname;
    private ChatType type;
    private String content;

    public ChatMessage(Long bookClubId, LocalDateTime date, Long userId, String userNickname, ChatType type,  String content) {
        this.bookClubId = bookClubId;
        this.date = date;
        this.userId = userId;
        this.userNickname = userNickname;
        this.type = type;
        this.content = content;
    }
}
