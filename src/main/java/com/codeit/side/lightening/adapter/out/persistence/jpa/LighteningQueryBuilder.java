package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.domain.Category;
import com.querydsl.core.BooleanBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningEntity.lighteningEntity;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LighteningQueryBuilder {
    private final BooleanBuilder booleanBuilder;

    public static LighteningQueryBuilder Builder() {
        return new LighteningQueryBuilder(new BooleanBuilder());
    }

    public BooleanBuilder build() {
        return booleanBuilder;
    }

    public LighteningQueryBuilder addCategoryCondition(String category) {
        if (category != null) {
            booleanBuilder.and(lighteningEntity.category.eq(Category.from(category)));
        }
        return this;
    }

    public LighteningQueryBuilder addCityCondition(String city) {
        if (city != null) {
            booleanBuilder.and(lighteningEntity.city.eq(city));
        }
        return this;
    }

    public LighteningQueryBuilder addTownCondition(String town) {
        if (town != null) {
            booleanBuilder.and(lighteningEntity.town.eq(town));
        }
        return this;
    }

    public LighteningQueryBuilder addTargetAtCondition(LocalDateTime targetAt) {
        if (targetAt != null) {
            booleanBuilder.and(lighteningEntity.targetAt.eq(targetAt));
        }
        return this;
    }
}
