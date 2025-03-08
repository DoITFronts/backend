package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.domain.Category;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningEntity.lighteningEntity;
import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningLikeEntity.lighteningLikeEntity;

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
            booleanBuilder.and(lighteningEntity.targetAt.goe(targetAt))
                    .and(lighteningEntity.targetAt.lt(targetAt.plusDays(1)));
        }
        return this;
    }

    public LighteningQueryBuilder addMyCreatedCondition(String createdBy) {
        if (createdBy != null) {
            booleanBuilder.and(lighteningEntity.host.eq(createdBy));
        }
        return this;
    }

    public LighteningQueryBuilder addIsInactiveCondition(boolean isInactive) {
        booleanBuilder.and(lighteningEntity.isInactive.eq(isInactive));
        return this;
    }

    public LighteningQueryBuilder addLikeCondition(String email) {
        if (email != null) {
            booleanBuilder.and(lighteningEntity.id.in(
                    JPAExpressions.select(lighteningLikeEntity.lighteningId)
                            .from(lighteningLikeEntity)
                            .where(lighteningLikeEntity.email.eq(email), lighteningLikeEntity.isDeleted.eq(false))
            ));
        }
        return this;
    }
}
