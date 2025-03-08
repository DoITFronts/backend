package com.codeit.side.chat.application.port.in;

import com.codeit.side.chat.domain.ChatMessage;
import com.codeit.side.chat.domain.ChatMessages;
import com.codeit.side.chat.domain.ChatRoom;
import com.codeit.side.chat.domain.ChatRoomInfo;
import com.codeit.side.chat.domain.command.ChatRoomCommand;

import java.util.List;

public interface ChatMessageUseCase {
    void save(ChatMessage chatMessage);

    ChatRoom joinChatRoom(String email, ChatRoomCommand chatRoomCommand);

    List<ChatRoomInfo> findAllChatRooms(String email);

    void findChatRoomBy(Long id, Long userId);

    ChatMessages findAllMessagesByRoomId(Long roomId, String email, Long offset, Integer size);
}
