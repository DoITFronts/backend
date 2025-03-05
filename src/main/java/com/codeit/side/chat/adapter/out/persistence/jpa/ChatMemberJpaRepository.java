package com.codeit.side.chat.adapter.out.persistence.jpa;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemberJpaRepository extends JpaRepository<ChatMemberEntity, Long> {
}
