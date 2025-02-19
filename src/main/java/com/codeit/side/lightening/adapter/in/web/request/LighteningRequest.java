package com.codeit.side.lightening.adapter.in.web.request;

import com.codeit.side.lightening.domain.Category;
import com.codeit.side.lightening.domain.Lightening;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LighteningRequest(
        @NotNull String title,
        @NotNull String summary,
        @NotNull String address1,
        @NotNull String address2,
        @NotNull String category,
        @NotNull LocalDateTime targetAt,
        @NotNull LocalDateTime endAt,
        @NotNull @Min(5) @Max(20) Integer capacity
) {
    public Lightening toModel(boolean hasImage) {
        return Lightening.of(
                title,
                summary,
                address1 + " " + address2,
                address1,
                address2,
                Category.from(category),
                targetAt,
                endAt,
                capacity
        );
    }
}
