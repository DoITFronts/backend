package com.codeit.side.chat.application.port.out;

import com.codeit.side.chat.domain.command.ChatRoomCommand;

public interface ChatMemberRepository {
    void save(Long id, ChatRoomCommand chatRoomCommand);
}
