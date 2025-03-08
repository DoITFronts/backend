package com.codeit.side.chat.adapter.out.persistence.entity;

import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.ChatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "chat_message",
        indexes = @Index(name = "idx_chat_room", columnList = "chatRoomId")
)
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatRoomId;

    private LocalDateTime createdAt;

    private Long userId;

    private String userNickname;

    private ChatType type;

    private String content;

    public static ChatMessageEntity from(ChatMessage chatMessage) {
        return new ChatMessageEntity(
                chatMessage.getId(),
                chatMessage.getRoomId(),
                chatMessage.getCreatedAt(),
                chatMessage.getUserId(),
                chatMessage.getUserNickname(),
                chatMessage.getType(),
                chatMessage.getContent()
        );
    }

    public ChatMessage toDomain() {
        return ChatMessage.of(id, chatRoomId, createdAt, userId, userNickname, type, content);
    }
}