package com.codeit.side.chat.application.port.out;

import com.codeit.side.chat.domain.ChatMessage;

public interface ChatMessageRepository {
    void save(ChatMessage chatMessage);
}