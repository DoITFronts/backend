package com.codeit.side.lightening.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Lightening {
    private final Long id;
    private final String title;
    private final String location1;
    private final String location2;
    private final Category category;
    private final LocalDateTime targetAt;
    private final LocalDateTime endAt;
    private final Integer amount;

    public static Lightening of(
            String title,
            String location1,
            String location2,
            Category category,
            LocalDateTime targetAt,
            LocalDateTime endAt,
            Integer amount
    ){
        return of(null, title, location1, location2, category, targetAt, endAt, amount);
    }

    public static Lightening of(
            Long id,
            String title,
            String location1,
            String location2,
            Category category,
            LocalDateTime targetAt,
            LocalDateTime endAt,
            Integer amount
    ){
        return new Lightening(id, title, location1, location2, category, targetAt, endAt, amount);
    }
}
