package com.codeit.side.chat.adapter.out.persistence;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMemberEntity;
import com.codeit.side.chat.adapter.out.persistence.jpa.ChatMemberJpaRepository;
import com.codeit.side.chat.application.port.out.ChatMemberRepository;
import com.codeit.side.chat.domain.command.ChatRoomCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMemberRepositoryImpl implements ChatMemberRepository {
    private final ChatMemberJpaRepository chatMemberJpaRepository;

    @Override
    public void save(Long id, ChatRoomCommand chatRoomCommand) {
        List<ChatMemberEntity> chatMemberEntities = chatRoomCommand.getUserIds()
                .stream()
                .map(userId -> chatMemberJpaRepository.save(ChatMemberEntity.of(id, userId)))
                .toList();
        ChatMemberEntity chatMemberEntity = ChatMemberEntity.of(id, chatRoomCommand.getHostId());
        chatMemberEntities.add(chatMemberEntity);
        chatMemberJpaRepository.saveAll(chatMemberEntities);
    }
}
