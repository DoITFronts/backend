package com.codeit.side.chat.adapter.out.persistence.jpa;

import com.codeit.side.chat.adapter.out.persistence.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomEntity, Long> {
}
