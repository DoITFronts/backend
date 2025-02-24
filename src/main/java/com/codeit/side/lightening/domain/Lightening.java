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
    private final String summary;
    private final String address;
    private final String city;
    private final String town;
    private final Category category;
    private final LocalDateTime targetAt;
    private final LocalDateTime endAt;
    private final Integer capacity;
    private final Boolean hasImage;

    public static Lightening of(
            String title,
            String summary,
            String address,
            String city,
            String town,
            Category category,
            LocalDateTime targetAt,
            LocalDateTime endAt,
            Integer capacity,
            Boolean hasImage
    ){
        return of(null, title, summary, address, city, town, category, targetAt, endAt, capacity, hasImage);
    }

    public static Lightening of(
            Long id,
            String title,
            String summary,
            String address,
            String city,
            String town,
            Category category,
            LocalDateTime targetAt,
            LocalDateTime endAt,
            Integer capacity,
            Boolean hasImage
    ){
        return new Lightening(id, title, summary, address, city, town, category, targetAt, endAt, capacity, hasImage);
    }

}
