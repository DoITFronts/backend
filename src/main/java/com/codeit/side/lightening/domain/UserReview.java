package com.codeit.side.lightening.domain;

import com.codeit.side.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserReview {
    private final Review review;
    private final User user;

    public static UserReview of(Review review, User user) {
        return new UserReview(review, user);
    }
}
