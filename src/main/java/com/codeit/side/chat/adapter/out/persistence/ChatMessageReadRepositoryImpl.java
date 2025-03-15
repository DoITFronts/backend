package com.codeit.side.chat.adapter.out.persistence;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMessageReadEntity;
import com.codeit.side.chat.adapter.out.persistence.jpa.ChatMessageReadJpaRepository;
import com.codeit.side.chat.application.port.out.ChatMessageReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageReadRepositoryImpl implements ChatMessageReadRepository {
    private final ChatMessageReadJpaRepository chatMessageReadJpaRepository;

    @Override
    public void save(Long roomId, List<Long> userIds) {
        List<ChatMessageReadEntity> chatMessageReadEntities = userIds.stream()
                .map(userId -> ChatMessageReadEntity.of(roomId, userId))
                .toList();
        chatMessageReadJpaRepository.saveAll(chatMessageReadEntities);
    }

    @Override
    public void read(Long roomId, Long userId) {
        chatMessageReadJpaRepository.findAllByChatRoomIdAndUserIdAndIsReadFalse(roomId, userId)
                .forEach(ChatMessageReadEntity::read);
    }
}
