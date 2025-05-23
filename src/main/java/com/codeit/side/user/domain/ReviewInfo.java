package com.codeit.side.user.domain;

import com.codeit.side.lightening.domain.Lightening;
import com.codeit.side.lightening.domain.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewInfo {
    private final Review review;
    private final Lightening lightening;
    private final User user;

    public static ReviewInfo of(Review review, Lightening lightening, User user) {
        return new ReviewInfo(review, lightening, user);
    }
}
