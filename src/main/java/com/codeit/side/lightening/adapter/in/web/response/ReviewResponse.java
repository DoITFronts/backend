package com.codeit.side.lightening.adapter.in.web.response;

import com.codeit.side.lightening.domain.Review;
import com.codeit.side.lightening.domain.UserReview;
import com.codeit.side.user.domain.User;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Integer score,
        String content,
        Long userId,
        String nickname,
        LocalDateTime createdAt
) {
    public static ReviewResponse from(UserReview userReview) {
        Review review = userReview.getReview();
        User user = userReview.getUser();
        return new ReviewResponse(
                review.getId(),
                review.getScore(),
                review.getContent(),
                user.getId(),
                user.getNickname(),
                review.getCreatedAt()
        );
    }
}
