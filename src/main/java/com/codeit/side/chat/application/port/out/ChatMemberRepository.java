package com.codeit.side.chat.application.port.out;

import com.codeit.side.chat.domain.command.ChatRoomCommand;

import java.util.List;
import java.util.Map;

public interface ChatMemberRepository {
    void save(Long id, ChatRoomCommand chatRoomCommand);

    Map<Long, Integer> findAllMemberCountByIds(List<Long> chatRoomIds);
}
