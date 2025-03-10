package com.codeit.side.chat.domain.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomCommand {
    private final String name;
    private final Long lighteningId;
    private final Long hostId;

    public static ChatRoomCommand of(String name, Long lighteningId, Long hostId) {
        return new ChatRoomCommand(name, lighteningId, hostId);
    }
}
