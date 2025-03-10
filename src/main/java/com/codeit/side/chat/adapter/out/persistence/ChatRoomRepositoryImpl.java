package com.codeit.side.chat.adapter.out.persistence;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatRoomEntity;
import com.codeit.side.chat.adapter.out.persistence.jpa.ChatRoomJpaQueryRepository;
import com.codeit.side.chat.adapter.out.persistence.jpa.ChatRoomJpaRepository;
import com.codeit.side.chat.application.port.out.ChatRoomRepository;
import com.codeit.side.chat.domain.ChatRoom;
import com.codeit.side.chat.domain.command.ChatRoomCommand;
import com.codeit.side.common.adapter.exception.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {
    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final ChatRoomJpaQueryRepository chatRoomJpaQueryRepository;

    @Override
    public ChatRoom save(ChatRoomCommand chatRoomCommand) {
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.of(chatRoomCommand.getName(), chatRoomCommand.getLighteningId(), chatRoomCommand.getHostId());
        return chatRoomJpaRepository.save(chatRoomEntity)
                .toDomain();
    }

    @Override
    public List<ChatRoom> findAllByUserId(Long id) {
        return chatRoomJpaQueryRepository.findAllByUserId(id)
                .stream()
                .map(ChatRoomEntity::toDomain)
                .toList();
    }

    @Override
    public ChatRoom getBy(Long id) {
        return chatRoomJpaRepository.findById(id)
                .orElseThrow(ChatRoomNotFoundException::new)
                .toDomain();
    }

    @Override
    public ChatRoom getByLighteningId(Long lighteningId) {
        return chatRoomJpaRepository.findByLighteningId(lighteningId)
                .orElseThrow(ChatRoomNotFoundException::new)
                .toDomain();
    }

    @Override
    public List<ChatRoom> findAllByLighteningIds(List<Long> lighteningIds) {
        return chatRoomJpaRepository.findAllByLighteningIdIn(lighteningIds)
                .stream()
                .map(ChatRoomEntity::toDomain)
                .toList();
    }
}
