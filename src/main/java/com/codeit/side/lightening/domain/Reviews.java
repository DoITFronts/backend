package com.codeit.side.lightening.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Reviews {
    private final List<UserReview> userReviews;
    private final Integer totalCount;

    public static Reviews of(List<UserReview> userReviews, Integer totalCount) {
        return new Reviews(userReviews, totalCount);
    }

    public Stream<UserReview> stream() {
        return userReviews.stream();
    }
}
