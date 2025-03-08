package com.codeit.side.user.adapter.in.web.response;

import com.codeit.side.user.domain.ReviewInfos;

import java.util.List;

public record ReviewInfoResponses(List<ReviewInfoResponse> reviews, Integer totalCount) {
    public static ReviewInfoResponses from(ReviewInfos reviewInfos) {
        List<ReviewInfoResponse> reviewInfoResponses = reviewInfos.stream()
                .map(reviewInfo -> ReviewInfoResponse.of(reviewInfo, reviewInfos.getUser()))
                .toList();
        return new ReviewInfoResponses(reviewInfoResponses, reviewInfos.getTotalCount());
    }
}
