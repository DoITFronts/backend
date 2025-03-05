package com.codeit.side.chat.application.port.out;

import com.codeit.side.chat.domain.command.ChatRoomCommand;

public interface ChatRoomRepository {
    Long save(ChatRoomCommand chatRoomCommand);
}
