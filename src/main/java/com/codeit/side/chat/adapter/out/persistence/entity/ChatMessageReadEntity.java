package com.codeit.side.chat.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "chat_message_read",
        indexes = @Index(name = "idx_chat_room", columnList = "chatRoomId")
)
public class ChatMessageReadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatRoomId;

    private Long userId;

    private Boolean isRead;

    public static ChatMessageReadEntity of(Long chatRoomId, Long userId) {
        return new ChatMessageReadEntity(null, chatRoomId, userId, false);
    }
}
