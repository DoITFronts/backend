package com.codeit.side.chat.adapter.out.persistence;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMessageEntity;
import com.codeit.side.chat.adapter.out.persistence.jpa.ChatMessageJpaRepository;
import com.codeit.side.chat.application.port.out.ChatMessageRepository;
import com.codeit.side.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatMessageRepository {
    private final ChatMessageJpaRepository chatMessageJpaRepository;

    @Override
    @Transactional
    public void save(ChatMessage chatMessage) {
        chatMessageJpaRepository.save(ChatMessageEntity.from(chatMessage));
    }

//    @Override
//    @Transactional(readOnly = true)
//    public ChatMessage loadRecentChat(Long bookClubId) {
//        return chatMessageRepository
//                .findTopByBookClubIdOrderByDateDesc(bookClubId)
//                .orElse(new ChatMessageEntity())
//                .toDomain();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ChatMessage> loadAllChat(Long bookClubId) {
//        return chatMessageRepository
//                .findByBookClubId(bookClubId)
//                .orElse(new ArrayList<>())
//                .stream().map(ChatMessageEntity::toDomain).toList();
//    }
}