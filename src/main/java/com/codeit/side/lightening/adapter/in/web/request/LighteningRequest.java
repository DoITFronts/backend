package com.codeit.side.lightening.adapter.in.web.request;

import com.codeit.side.lightening.domain.Category;
import com.codeit.side.lightening.domain.Lightening;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LighteningRequest(
        @NotNull(message = "title는 필수입니다.")
        String title,

        @NotNull(message = "summary는 필수입니다.")
        String summary,

        @NotNull(message = "address는 필수입니다.")
        String address,

        @NotNull(message = "city은 필수입니다.")
        String city,

        @NotNull(message = "town은 필수입니다.")
        String town,

        @NotNull(message = "category는 필수입니다.")
        String category,

        @NotNull(message = "targetAt은 필수입니다.")
        LocalDateTime targetAt,

        @NotNull(message = "endAt은 필수입니다.")
        LocalDateTime endAt,

        @NotNull(message = "capacity는 필수입니다.")
        @Max(value = 20, message = "20 이하만 가능합니다.")
        Integer capacity,

        @NotNull(message = "minCapacity는 필수입니다.")
        @Min(value = 1, message = "1 이상 5이하만 가능합니다.")
        @Max(value = 5, message = "1 이상 5이하만 가능합니다.")
        Integer minCapacity,

        @NotNull(message = "placeName은 필수입니다.")
        String placeName,

        @NotNull(message = "latitude는 필수입니다.")
        String latitude,

        @NotNull(message = "longitude는 필수입니다.")
        String longitude
) {
    public Lightening toModel(boolean hasImage) {
        return Lightening.of(
                title,
                summary,
                address,
                city,
                town,
                Category.from(category),
                targetAt,
                endAt,
                capacity,
                hasImage,
                minCapacity,
                placeName,
                latitude,
                longitude
        );
    }
}
