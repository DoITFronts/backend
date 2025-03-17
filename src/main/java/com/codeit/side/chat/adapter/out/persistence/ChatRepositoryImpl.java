package com.codeit.side.chat.adapter.out.persistence;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMessageEntity;
import com.codeit.side.chat.adapter.out.persistence.jpa.ChatMessageJpaRepository;
import com.codeit.side.chat.adapter.out.persistence.jpa.ChatMessageQueryRepository;
import com.codeit.side.chat.application.port.out.ChatMessageRepository;
import com.codeit.side.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatMessageRepository {
    private final ChatMessageJpaRepository chatMessageJpaRepository;
    private final ChatMessageQueryRepository chatMessageQueryRepository;

    @Override
    @Transactional
    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageJpaRepository.save(ChatMessageEntity.from(chatMessage))
                .toDomain();
    }

    @Override
    public Map<Long, ChatMessage> findAllLastMessageByIds(List<Long> chatRoomIds) {
        return chatRoomIds.stream()
                .map(id -> chatMessageQueryRepository.findAllLastMessageById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(ChatMessageEntity::getChatRoomId, ChatMessageEntity::toDomain));
    }

    @Override
    public List<ChatMessage> findAllByRoomId(Long roomId, Long offset, Integer size) {
        return chatMessageQueryRepository.findAllByRoomId(roomId, offset, size)
                .stream()
                .map(ChatMessageEntity::toDomain)
                .toList();
    }

}