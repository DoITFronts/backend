package com.codeit.side.chat.adapter.in.web.response;

import com.codeit.side.chat.domain.UserChatMessage;
import com.codeit.side.user.domain.User;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        Long id,
        Long roomId,
        Long userId,
        String userNickname,
        String content,
        LocalDateTime createdAt,
        String userImage
) {
    public static ChatMessageResponse from(UserChatMessage chatMessage) {
        User user = chatMessage.getUser();
        return new ChatMessageResponse(
                chatMessage.getChatMessage().getId(),
                chatMessage.getChatMessage().getRoomId(),
                chatMessage.getChatMessage().getUserId(),
                chatMessage.getChatMessage().getUserNickname(),
                chatMessage.getChatMessage().getContent(),
                chatMessage.getChatMessage().getCreatedAt(),
                user.isHasImage() ? "https://codeit-doit.s3.ap-northeast-2.amazonaws.com/user/%s/image.jpg".formatted(user.getId()) : ""
        );
    }
}
