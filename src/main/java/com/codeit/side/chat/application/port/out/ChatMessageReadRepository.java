package com.codeit.side.chat.application.port.out;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMessageReadEntity;

import java.util.List;

public interface ChatMessageReadRepository {
    void save(Long roomId, List<Long> userIds);

    void read(Long roomId, Long userId);

    List<ChatMessageReadEntity> findAllUnreadMessages(Long id, List<Long> chatRoomIds);

    int findUnreadMessagesBy(Long lighteningId, Long userId);
}
