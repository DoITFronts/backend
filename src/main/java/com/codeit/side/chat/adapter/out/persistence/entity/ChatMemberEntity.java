package com.codeit.side.chat.adapter.out.persistence.entity;

import com.codeit.side.common.adapter.out.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "chat_members")
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatRoomId;

    private Long userId;

    public static ChatMemberEntity of(Long chatRoomId, Long userId) {
        return ChatMemberEntity.builder()
                .chatRoomId(chatRoomId)
                .userId(userId)
                .build();
    }
}
