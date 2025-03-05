package com.codeit.side.chat.adapter.in.web.request;

import com.codeit.side.chat.domain.command.ChatRoomCommand;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ChatRequest(
        @NotNull(message = "채팅방 이름은 필수입니다.") String name,
        @NotNull(message = "유저는 필수입니다.") List<Long> userIds) {
    public static ChatRequest of(String name, List<Long> userIds) {
        return new ChatRequest(name, userIds);
    }

    public ChatRoomCommand toCommand() {
        return ChatRoomCommand.of(name, userIds);
    }
}
