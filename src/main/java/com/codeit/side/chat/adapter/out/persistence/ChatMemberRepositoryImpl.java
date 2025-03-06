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
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class ChatMemberRepositoryImpl implements ChatMemberRepository {
    private final ChatMemberJpaRepository chatMemberJpaRepository;

    @Override
    public void save(Long id, ChatRoomCommand chatRoomCommand) {
        List<ChatMemberEntity> chatMemberEntities = chatRoomCommand.getUserIds()
                .stream()
                .map(userId -> chatMemberJpaRepository.save(ChatMemberEntity.of(id, userId)))
                .toList();
        ChatMemberEntity chatMemberEntity = ChatMemberEntity.of(id, chatRoomCommand.getHostId());
        List<ChatMemberEntity> list = Stream.concat(chatMemberEntities.stream(), Stream.of(chatMemberEntity))
                .toList();
        chatMemberJpaRepository.saveAll(list);
    }

    @Override
    public Map<Long, Integer> findAllMemberCountByIds(List<Long> chatRoomIds) {
        return chatRoomIds.stream()
                .collect(Collectors.toMap(
                        chatRoomId -> chatRoomId,
                        chatRoomId -> chatMemberJpaRepository.findAllByChatRoomId(chatRoomId).size()
                ));
    }
}
