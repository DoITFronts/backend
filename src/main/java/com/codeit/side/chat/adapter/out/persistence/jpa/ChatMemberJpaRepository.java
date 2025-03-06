package com.codeit.side.chat.adapter.out.persistence.jpa;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMemberJpaRepository extends JpaRepository<ChatMemberEntity, Long> {
    List<ChatMemberEntity> findAllByChatRoomId(Long chatRoomId);
}
