package com.codeit.side.lightening.domain;

import static org.junit.jupiter.api.Assertions.*;

public class LighteningFixture {
    public static Lightening create(Lightening lightening) {
        return Lightening.of(
                lightening.getTitle(),
                lightening.getLocation1(),
                lightening.getLocation2(),
                lightening.getCategory(),
                lightening.getTargetAt(),
                lightening.getEndAt(),
                lightening.getAmount()
        );
    }

    public static Lightening create(Long id, Lightening lightening) {
        return Lightening.of(
                id,
                lightening.getTitle(),
                lightening.getLocation1(),
                lightening.getLocation2(),
                lightening.getCategory(),
                lightening.getTargetAt(),
                lightening.getEndAt(),
                lightening.getAmount()
        );
    }
}