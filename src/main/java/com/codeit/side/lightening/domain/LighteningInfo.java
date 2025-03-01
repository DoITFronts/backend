package com.codeit.side.lightening.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LighteningInfo {
    private final Lightening lightening;
    private final List<LighteningMember> lighteningMembers;
    private final boolean isLiked;

    public static LighteningInfo of(
            Lightening lightening,
            List<LighteningMember> lighteningMembers,
            boolean isLiked
    ){
        return new LighteningInfo(lightening, lighteningMembers, isLiked);
    }
}
