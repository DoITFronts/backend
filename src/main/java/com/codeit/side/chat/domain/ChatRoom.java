package com.codeit.side.chat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoom {
    private final Long id;
    private final String name;
    private final Long hostId;
    private final List<Long> userIds;

    public static ChatRoom of(Long id, String name, Long hostId) {
        return of(id, name, hostId, null);
    }

    public static ChatRoom of(Long id, String name, Long hostId, List<Long> userIds) {
        return new ChatRoom(id, name, hostId, userIds);
    }
}
