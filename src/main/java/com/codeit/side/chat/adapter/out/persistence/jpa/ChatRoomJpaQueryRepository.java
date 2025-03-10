package com.codeit.side.chat.adapter.out.persistence.jpa;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatRoomEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.codeit.side.chat.adapter.out.persistence.entity.QChatMemberEntity.chatMemberEntity;
import static com.codeit.side.chat.adapter.out.persistence.entity.QChatRoomEntity.chatRoomEntity;

@Repository
@RequiredArgsConstructor
public class ChatRoomJpaQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<ChatRoomEntity> findAllByUserId(Long id) {
        return jpaQueryFactory.selectFrom(chatRoomEntity)
                .innerJoin(chatMemberEntity)
                .on(chatRoomEntity.id.eq(chatMemberEntity.chatRoomId), chatMemberEntity.userId.eq(id))
                .orderBy(chatRoomEntity.createdAt.desc())
                .fetch();
    }
}
