package com.codeit.side.lightening.adapter.out.persistence;

import com.codeit.side.lightening.domain.Review;

import java.time.LocalDateTime;

public record ReviewDto(
        Long id,
        Integer score,
        String content,
        Long lighteningId,
        Long userId,
        LocalDateTime createdAt,
        Long count
) {
    public Review toDomain() {
        return Review.of(id, lighteningId, userId, score, content, createdAt);
    }
}
