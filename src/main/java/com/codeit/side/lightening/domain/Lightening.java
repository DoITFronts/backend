package com.codeit.side.lightening.domain;

import com.codeit.side.common.adapter.exception.IllegalRequestException;
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
    private final String description;
    private final String address;
    private final String city;
    private final String town;
    private final Category category;
    private final LocalDateTime targetAt;
    private final LocalDateTime endAt;
    private final Integer capacity;
    private final Boolean hasImage;
    private final String hostEmail;
    private final Integer minCapacity;
    private final String placeName;
    private final String latitude;
    private final String longitude;

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
            Boolean hasImage,
            Integer minCapacity,
            String placeName,
            String latitude,
            String longitude
    ){
        return of(null, title, summary, null, address, city, town, category, targetAt, endAt, capacity, hasImage, null, minCapacity, placeName, latitude, longitude);
    }

    public static Lightening of(
            Long id,
            String title,
            String summary,
            String description,
            String address,
            String city,
            String town,
            Category category,
            LocalDateTime targetAt,
            LocalDateTime endAt,
            Integer capacity,
            Boolean hasImage,
            String hostEmail,
            Integer minCapacity,
            String placeName,
            String latitude,
            String longitude
    ){
        validateCapacity(capacity, minCapacity);
        return new Lightening(id, title, summary, description, address, city, town, category, targetAt, endAt, capacity, hasImage, hostEmail, minCapacity, placeName, latitude, longitude);
    }

    private static void validateCapacity(Integer capacity, Integer minCapacity) {
        if (minCapacity > capacity) {
            throw new IllegalRequestException("minCapacity는 capacity보다 작거나 같아야 합니다.");
        }
    }

    public boolean isNotJoinable(int currentMemberCount) {
        return capacity <= currentMemberCount;
    }
}
