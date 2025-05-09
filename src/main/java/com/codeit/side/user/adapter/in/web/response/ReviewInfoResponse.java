package com.codeit.side.user.adapter.in.web.response;

import com.codeit.side.lightening.domain.Category;
import com.codeit.side.lightening.domain.Lightening;
import com.codeit.side.lightening.domain.Review;
import com.codeit.side.user.domain.ReviewInfo;
import com.codeit.side.user.domain.User;

import java.time.LocalDateTime;

public record ReviewInfoResponse(
        Long reviewId,
        String reviewContent,
        Integer rating,
        LocalDateTime createdAt,
        Long lighteningId,
        Category category,
        String title,
        String city,
        String town,
        String lighteningImageUrl,
        LocalDateTime targetAt,
        Long userId,
        String nickname,
        String userImageUrl
) {

    public static ReviewInfoResponse from(ReviewInfo reviewInfo) {
        Review review = reviewInfo.getReview();
        User user = reviewInfo.getUser();
        Lightening lightening = reviewInfo.getLightening();
        return new ReviewInfoResponse(
                review.getId(),
                review.getContent(),
                review.getScore(),
                review.getCreatedAt(),
                lightening.getId(),
                lightening.getCategory(),
                lightening.getTitle(),
                lightening.getCity(),
                lightening.getTown(),
                lightening.getHasImage() ? "https://codeit-doit.s3.ap-northeast-2.amazonaws.com/lightening/%s/image.jpg".formatted(lightening.getId()) : "",
                lightening.getTargetAt(),
                user.getId(),
                user.getNickname(),
                user.isHasImage() ? "https://codeit-doit.s3.ap-northeast-2.amazonaws.com/user/%s/image.jpg".formatted(user.getId()) : ""
        );
    }
}
