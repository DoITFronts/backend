package com.codeit.side.chat.application.port.out;

import com.codeit.side.chat.domain.ChatRoom;
import com.codeit.side.chat.domain.command.ChatRoomCommand;

import java.util.List;

public interface ChatRoomRepository {
    ChatRoom save(ChatRoomCommand chatRoomCommand);

    List<ChatRoom> findAllByUserId(Long id);

    ChatRoom getBy(Long id);
}
