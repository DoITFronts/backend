package com.codeit.side.lightening.adapter.out.persistence;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningEntity;
import com.codeit.side.lightening.domain.Category;

import java.time.LocalDateTime;

public record LighteningDto(
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
        String host,
        Integer minCapacity,
        String placeName,
        String latitude,
        String longitude,
        Long count
) {
    public LighteningEntity toEntity() {
        return LighteningEntity.of(
                id,
                title,
                summary,
                description,
                address,
                city,
                town,
                category,
                targetAt,
                endAt,
                capacity,
                hasImage,
                host,
                minCapacity,
                placeName,
                latitude,
                longitude
        );
    }
}
