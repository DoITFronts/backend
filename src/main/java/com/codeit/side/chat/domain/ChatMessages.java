package com.codeit.side.chat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessages {
    private final List<UserChatMessage> messages;
    private final boolean isLast;

    public static ChatMessages of(List<UserChatMessage> messages, boolean isLast) {
        return new ChatMessages(messages, isLast);
    }

    public Stream<UserChatMessage> stream() {
        return messages.stream();
    }
}
