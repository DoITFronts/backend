package com.codeit.side.lightening.adapter.in.web.response;

import com.codeit.side.lightening.domain.Reviews;

import java.util.List;

public record ReviewResponses(List<ReviewResponse> reviews, Integer totalCount) {
    public static ReviewResponses from(Reviews reviews) {
        List<ReviewResponse> reviewResponses = reviews.stream()
                .map(ReviewResponse::from)
                .toList();
        return new ReviewResponses(reviewResponses, reviews.getTotalCount());
    }
}
