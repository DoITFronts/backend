package com.codeit.side.chat.adapter.in.websocket.request;

import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.ChatType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ChatMessageSend {
    private Long id;
    private Long bookClubId;
    private LocalDateTime date;
    private Long userId;
    private String userNickname;
    private ChatType type;
    private String content;
    private String image;

    public static ChatMessageSend from(ChatMessage chatMessage, String image) {
        ChatMessageSend messageSend = new ChatMessageSend(
                chatMessage.getId(),
                chatMessage.getBookClubId(),
                chatMessage.getDate(),
                chatMessage.getUserId(),
                chatMessage.getUserNickname(),
                chatMessage.getType(),
                chatMessage.getContent(),
                image
        );
        return messageSend;
    }
}
