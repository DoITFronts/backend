package com.codeit.side.chat.adapter.out.persistence.jpa;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMessageEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.codeit.side.chat.adapter.out.persistence.entity.QChatMessageEntity.chatMessageEntity;

@Repository
@RequiredArgsConstructor
public class ChatMessageQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<ChatMessageEntity> findAllLastMessageById(Long chatRoomId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(chatMessageEntity)
                .where(chatMessageEntity.chatRoomId.eq(chatRoomId))
                .orderBy(chatMessageEntity.createdAt.desc())
                .fetchFirst());
    }

    public List<ChatMessageEntity> findAllByRoomId(Long roomId, Long offset, Integer size) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(chatMessageEntity.chatRoomId.eq(roomId));
        if (offset != null) {
            booleanBuilder.and(chatMessageEntity.id.lt(offset));
        }
        return jpaQueryFactory.selectFrom(chatMessageEntity)
                .where(booleanBuilder)
                .orderBy(chatMessageEntity.id.desc())
                .limit(size + 1)
                .fetch();
    }
}
