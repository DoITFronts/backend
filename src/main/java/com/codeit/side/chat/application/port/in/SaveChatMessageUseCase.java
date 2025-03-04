package com.codeit.side.chat.application.port.in;

import com.codeit.side.chat.domain.ChatMessage;

public interface SaveChatMessageUseCase {
    void save(ChatMessage chatMessage);
}
