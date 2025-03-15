package com.codeit.side.chat.adapter.out.persistence;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMemberEntity;
import com.codeit.side.chat.adapter.out.persistence.jpa.ChatMemberJpaRepository;
import com.codeit.side.chat.application.port.out.ChatMemberRepository;
import com.codeit.side.chat.domain.command.ChatRoomCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ChatMemberRepositoryImpl implements ChatMemberRepository {
    private final ChatMemberJpaRepository chatMemberJpaRepository;

    @Override
    public void save(Long id, ChatRoomCommand chatRoomCommand) {
        ChatMemberEntity chatMemberEntity = ChatMemberEntity.of(id, chatRoomCommand.getHostId());
        chatMemberJpaRepository.save(chatMemberEntity);
    }

    @Override
    public Map<Long, Integer> findAllMemberCountByIds(List<Long> chatRoomIds) {
        return chatRoomIds.stream()
                .collect(Collectors.toMap(
                        chatRoomId -> chatRoomId,
                        chatRoomId -> chatMemberJpaRepository.findAllByChatRoomId(chatRoomId).size()
                ));
    }

    @Override
    public boolean existsByChatRoomIdAndUserId(Long id, Long userId) {
        return chatMemberJpaRepository.findByChatRoomIdAndUserId(id, userId)
                .isPresent();
    }

    @Override
    public void join(Long id, Long userId) {
        chatMemberJpaRepository.save(ChatMemberEntity.of(id, userId));
    }

    @Override
    public List<ChatMemberEntity> findAllMemberById(Long roomId) {
        return chatMemberJpaRepository.findAllByChatRoomId(roomId);
    }
}
