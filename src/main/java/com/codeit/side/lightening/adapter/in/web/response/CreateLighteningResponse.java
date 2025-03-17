package com.codeit.side.lightening.adapter.in.web.response;

import com.codeit.side.lightening.domain.LighteningChatRoom;

public record CreateLighteningResponse(long id, long chatRoomId) {
    public static CreateLighteningResponse from(LighteningChatRoom lighteningChatRoom) {
        return new CreateLighteningResponse(lighteningChatRoom.getLightening().getId(), lighteningChatRoom.getChatRoom().getId());
    }
}
