package com.codeit.side.lightening.domain;

import com.codeit.side.chat.domain.ChatRoom;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LighteningChatRoom {
    private final Lightening lightening;
    private final ChatRoom chatRoom;

    public static LighteningChatRoom of(Lightening lightening, ChatRoom chatRoom){
        return new LighteningChatRoom(lightening, chatRoom);
    }
}
