package com.codeit.side.chat.adapter.out.persistence;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatRoomEntity;
import com.codeit.side.chat.adapter.out.persistence.jpa.ChatRoomJpaRepository;
import com.codeit.side.chat.application.port.out.ChatRoomRepository;
import com.codeit.side.chat.domain.command.ChatRoomCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
