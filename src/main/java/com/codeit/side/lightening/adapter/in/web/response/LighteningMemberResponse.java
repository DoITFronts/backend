package com.codeit.side.lightening.adapter.in.web.response;

import com.codeit.side.lightening.domain.LighteningMember;

public record LighteningMemberResponse(
        Long lighteningId,
        Long userId,
        String email,
        String name,
        String description,
        String image,
        boolean isHost
) {
    public static LighteningMemberResponse from(LighteningMember lighteningMember, String hostEmail) {
        return new LighteningMemberResponse(
                lighteningMember.getLighteningId(),
                lighteningMember.getUserId(),
                lighteningMember.getUserEmail(),
                lighteningMember.getUserName(),
                lighteningMember.getDescription(),
                lighteningMember.isHasImage() ? "https://codeit-doit.s3.ap-northeast-2.amazonaws.com/user/%s/image.jpg".formatted(lighteningMember.getUserId()) : "",
                lighteningMember.isHost(hostEmail)
        );
    }
}
