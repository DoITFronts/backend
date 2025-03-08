package com.codeit.side.lightening.domain.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewCommand {
    private final Long lighteningId;
    private final Long userId;
    private final Integer score;
    private final String content;

    public static ReviewCommand of(Integer score, String content) {
        return of(null, null, score, content);
    }

    public static ReviewCommand of(Long lighteningId, Long userId, Integer score, String content) {
        return new ReviewCommand(lighteningId, userId, score, content);
    }
}
