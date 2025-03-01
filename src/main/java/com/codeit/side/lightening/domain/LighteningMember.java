package com.codeit.side.lightening.domain;

import com.codeit.side.lightening.adapter.out.persistence.LighteningMemberDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LighteningMember {
    private final Long lighteningId;
    private final Long userId;
    private final String userEmail;
    private final String userName;

    public static LighteningMember from(LighteningMemberDto lighteningMemberDto) {
        return new LighteningMember(
                lighteningMemberDto.lighteningId(),
                lighteningMemberDto.userId(),
                lighteningMemberDto.userEmail(),
                lighteningMemberDto.userName()
        );
    }

    public boolean isHost(String hostEmail) {
        return userEmail.equals(hostEmail);
    }
}
