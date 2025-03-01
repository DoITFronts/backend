package com.codeit.side.lightening.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LighteningLike {
    private final Long lighteningId;
    private final String email;

    public static LighteningLike of(Long lighteningId, String userId) {
        return new LighteningLike(lighteningId, userId);
    }
}
