package com.codeit.side.chat.adapter.out.persistence.jpa;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, Long> {
}