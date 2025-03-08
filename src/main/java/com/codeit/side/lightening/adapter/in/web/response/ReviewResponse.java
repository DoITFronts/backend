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
        String userImageUrl,
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
                user.isHasImage() ? "https://codeit-doit.s3.ap-northeast-2.amazonaws.com/user/%s/image.jpg".formatted(user.getId()) : "",
                review.getCreatedAt()
        );
    }
}
