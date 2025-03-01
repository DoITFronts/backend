package com.codeit.side.lightening.adapter.in.web.response;

import com.codeit.side.lightening.domain.LighteningMember;

public record LighteningMemberResponse(
        Long lighteningId,
        Long userId,
        String email,
        String name,
        boolean isHost
) {
    public static LighteningMemberResponse from(LighteningMember lighteningMember, String hostEmail) {
        return new LighteningMemberResponse(
                lighteningMember.getLighteningId(),
                lighteningMember.getUserId(),
                lighteningMember.getUserEmail(),
                lighteningMember.getUserName(),
                lighteningMember.isHost(hostEmail)
        );
    }
}
