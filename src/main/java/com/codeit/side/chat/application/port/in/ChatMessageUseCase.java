package com.codeit.side.chat.application.port.in;

import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.command.ChatRoomCommand;

public interface ChatMessageUseCase {
    void save(ChatMessage chatMessage);

    void joinChatRoom(String email, ChatRoomCommand chatRoomCommand);
}
