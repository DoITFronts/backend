package com.codeit.side.chat.adapter.out.persistence;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatRoomEntity;
import com.codeit.side.chat.adapter.out.persistence.jpa.ChatRoomJpaRepository;
import com.codeit.side.chat.application.port.out.ChatRoomRepository;
import com.codeit.side.chat.domain.ChatRoom;
import com.codeit.side.chat.domain.command.ChatRoomCommand;
import com.codeit.side.common.adapter.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {
    private final ChatRoomJpaRepository chatRoomJpaRepository;

    @Override
    public Long save(ChatRoomCommand chatRoomCommand) {
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.of(chatRoomCommand.getName(), chatRoomCommand.getHostId());
        return chatRoomJpaRepository.save(chatRoomEntity)
                .getId();
    }

    @Override
    public List<ChatRoom> findAllByUserId(Long id) {
        return chatRoomJpaRepository.findByHostId(id)
                .stream()
                .map(ChatRoomEntity::toDomain)
                .toList();
    }

    @Override
    public ChatRoom getBy(Long id) {
        return chatRoomJpaRepository.findById(id)
                .orElseThrow(UserNotFoundException::new)
                .toDomain();
    }
}
