package com.codeit.side.lightening.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Review {
    private final Long id;
    private final Long userId;
    private final Long lighteningId;
    private final Integer score;
    private final String content;
    private final LocalDateTime createdAt;

    public static Review of(Long id, Integer score, String content) {
        return of(id, null, null, score, content, null);
    }

    public static Review of(Long id, Long lighteningId, Long userId, Integer score, String content, LocalDateTime createdAt) {
        return new Review(id, userId, lighteningId, score, content, createdAt);
    }
}
