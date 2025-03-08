package com.codeit.side.lightening.domain;

import java.time.LocalDateTime;

public class LighteningFixture {
    public static Lightening create() {
        return Lightening.of(
                "title",
                "summary",
                "address",
                "city",
                "town",
                Category.ALCOHOL,
                LocalDateTime.of(2021, 1, 1, 0, 0),
                LocalDateTime.of(2021, 1, 2, 0, 0),
                100,
                false,
                1,
                null,
                null,
                null
        );
    }

    public static Lightening create(Long id, Lightening lightening) {
        return Lightening.of(
                id,
                lightening.getTitle(),
                lightening.getSummary(),
                lightening.getDescription(),
                lightening.getAddress(),
                lightening.getCity(),
                lightening.getTown(),
                lightening.getCategory(),
                lightening.getTargetAt(),
                lightening.getEndAt(),
                lightening.getCapacity(),
                false,
                null,
                1,
                null,
                null,
                null
        );
    }
}