package com.codeit.side.lightening.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LighteningCondition {
    private final String category;
    private final String city;
    private final String town;
    private final LocalDateTime targetAt;
    private final LighteningPaging lighteningPaging;

    public static LighteningCondition of(
            String category,
            String city,
            String town,
            LocalDateTime targetAt,
            LighteningPaging lighteningPaging
    ){
        return new LighteningCondition(category, city, town, targetAt, lighteningPaging);
    }

    public int getOffset() {
        return lighteningPaging.getOffset();
    }
}
