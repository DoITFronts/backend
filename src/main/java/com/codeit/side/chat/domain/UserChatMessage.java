package com.codeit.side.chat.domain;

import com.codeit.side.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserChatMessage {
    private final ChatMessage chatMessage;
    private final User user;

    public static UserChatMessage of(ChatMessage chatMessage, User user) {
        return new UserChatMessage(chatMessage, user);
    }
}
