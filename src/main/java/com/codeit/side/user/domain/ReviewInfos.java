package com.codeit.side.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewInfos {
    private final List<ReviewInfo> reviewInfos;
    private final User user;
    private final Integer totalCount;

    public static ReviewInfos of(List<ReviewInfo> reviewInfos, User user, Integer totalCount) {
        return new ReviewInfos(reviewInfos, user, totalCount);
    }

    public Stream<ReviewInfo> stream() {
        return reviewInfos.stream();
    }
}
