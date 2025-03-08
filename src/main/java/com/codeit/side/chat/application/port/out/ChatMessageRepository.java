package com.codeit.side.chat.application.port.out;

import com.codeit.side.chat.domain.ChatMessage;

import java.util.List;
import java.util.Map;

public interface ChatMessageRepository {
    void save(ChatMessage chatMessage);

    Map<Long, ChatMessage> findAllLastMessageByIds(List<Long> chatRoomIds);

    List<ChatMessage> findAllByRoomId(Long roomId, Long offset, Integer size);
}