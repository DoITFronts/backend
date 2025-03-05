package com.codeit.side.chat.domain.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomCommand {
    private final String name;
    private final Long hostId;
    private final List<Long> userIds;

    public static ChatRoomCommand of(String name, List<Long> userIds) {
        return of(name, null, userIds);
    }

    public static ChatRoomCommand of(String name, Long hostId, List<Long> userIds) {
        return new ChatRoomCommand(name, hostId, userIds);
    }
}
