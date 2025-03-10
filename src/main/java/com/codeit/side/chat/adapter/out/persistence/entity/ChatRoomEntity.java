package com.codeit.side.chat.adapter.out.persistence.entity;

import com.codeit.side.chat.domain.ChatRoom;
import com.codeit.side.common.adapter.out.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "chat_rooms")
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long lighteningId;

    private Long hostId;

    public static ChatRoomEntity of(String name, Long lighteningId, Long hostId) {
        return ChatRoomEntity.builder()
                .name(name)
                .lighteningId(lighteningId)
                .hostId(hostId)
                .build();
    }

    public ChatRoom toDomain() {
        return ChatRoom.of(id, name, lighteningId, hostId);
    }
}
