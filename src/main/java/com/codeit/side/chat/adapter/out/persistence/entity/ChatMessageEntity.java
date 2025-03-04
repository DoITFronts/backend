package com.codeit.side.chat.adapter.out.persistence.entity;

import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.ChatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "chat_message",
        indexes = @Index(name = "idx_book_club", columnList = "bookClubId")
)
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lighteningId;

    private LocalDateTime date;

    private Long userId;

    private String userNickname;

    private ChatType type;

    private String content;

    public static ChatMessageEntity from(ChatMessage chatMessage){
        return new ChatMessageEntity(
                chatMessage.getId(),
                chatMessage.getBookClubId(),
                chatMessage.getDate(),
                chatMessage.getUserId(),
                chatMessage.getUserNickname(),
                chatMessage.getType(),
                chatMessage.getContent()
        );
    }

    public ChatMessage toDomain(){
        return new ChatMessage(
                lighteningId,
                date,
                userId,
                userNickname,
                type,
                content
        );
    }
}