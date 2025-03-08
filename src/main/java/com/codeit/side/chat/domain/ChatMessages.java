package com.codeit.side.chat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessages {
    private final List<ChatMessage> messages;
    private final boolean isLast;

    public static ChatMessages of(List<ChatMessage> messages, boolean isLast) {
        return new ChatMessages(messages, isLast);
    }

    public Stream<ChatMessage> stream() {
        return messages.stream();
    }
}
