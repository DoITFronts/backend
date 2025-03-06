package com.codeit.side.chat.application.port.in;

import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.ChatRoom;
import com.codeit.side.chat.domain.command.ChatRoomCommand;

import java.util.List;

public interface ChatMessageUseCase {
    void save(ChatMessage chatMessage);

    void joinChatRoom(String email, ChatRoomCommand chatRoomCommand);

    List<ChatRoom> findAllChatRooms(String email);
}
