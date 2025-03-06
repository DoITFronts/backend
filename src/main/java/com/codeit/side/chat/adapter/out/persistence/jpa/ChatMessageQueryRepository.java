package com.codeit.side.chat.adapter.out.persistence.jpa;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMessageEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.codeit.side.chat.adapter.out.persistence.entity.QChatMessageEntity.chatMessageEntity;

@Repository
@RequiredArgsConstructor
public class ChatMessageQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<ChatMessageEntity> findAllLastMessageById(Long chatRoomId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(chatMessageEntity)
                .where(chatMessageEntity.chatRoomId.eq(chatRoomId))
                .orderBy(chatMessageEntity.date.desc())
                .fetchFirst());
    }
}
