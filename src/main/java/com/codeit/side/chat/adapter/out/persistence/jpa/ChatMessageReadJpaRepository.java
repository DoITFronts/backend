package com.codeit.side.chat.adapter.out.persistence.jpa;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMessageReadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ChatMessageReadJpaRepository extends JpaRepository<ChatMessageReadEntity, Long> {
    List<ChatMessageReadEntity> findAllByChatRoomIdAndUserIdAndIsReadFalse(Long roomId, Long userId);

    List<ChatMessageReadEntity> findAllByUserIdAndChatRoomIdInAndIsReadFalse(Long userId, Collection<Long> chatRoomIds);
}
