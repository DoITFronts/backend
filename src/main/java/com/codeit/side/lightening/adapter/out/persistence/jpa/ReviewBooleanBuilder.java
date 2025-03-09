package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.domain.Category;
import com.querydsl.core.BooleanBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningEntity.lighteningEntity;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewBooleanBuilder {
    private final BooleanBuilder booleanBuilder;

    public static ReviewBooleanBuilder Builder() {
        return new ReviewBooleanBuilder(new BooleanBuilder());
    }

    public BooleanBuilder build() {
        return booleanBuilder;
    }

    public ReviewBooleanBuilder addCategoryCondition(String category) {
        if (category != null) {
            booleanBuilder.and(lighteningEntity.category.eq(Category.from(category)));
        }
        return this;
    }

    public ReviewBooleanBuilder addCityCondition(String city) {
        if (city != null) {
            booleanBuilder.and(lighteningEntity.city.eq(city));
        }
        return this;
    }

    public ReviewBooleanBuilder addTownCondition(String town) {
        if (town != null) {
            booleanBuilder.and(lighteningEntity.town.eq(town));
        }
        return this;
    }

    public ReviewBooleanBuilder addTargetAtCondition(LocalDateTime targetAt) {
        if (targetAt != null) {
            booleanBuilder.and(lighteningEntity.targetAt.goe(targetAt))
                    .and(lighteningEntity.targetAt.lt(targetAt.plusDays(1)));
        }
        return this;
    }

    public ReviewBooleanBuilder addIsInactiveCondition(boolean isInactive) {
        booleanBuilder.and(lighteningEntity.isInactive.eq(isInactive));
        return this;
    }
}
