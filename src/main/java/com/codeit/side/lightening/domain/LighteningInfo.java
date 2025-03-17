package com.codeit.side.lightening.domain;

import com.codeit.side.chat.domain.ChatRoom;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LighteningInfo {
    private final Lightening lightening;
    private final List<LighteningMember> lighteningMembers;
    private final ChatRoom chatRoom;
    private final boolean isLiked;
    private final int unreadCount;

    public static LighteningInfo of(
            Lightening lightening,
            List<LighteningMember> lighteningMembers,
            ChatRoom chatRoom,
            boolean isLiked,
            int unreadCount
    ) {
        return new LighteningInfo(lightening, lighteningMembers, chatRoom, isLiked, unreadCount);
    }
}
