package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningEntity;
import com.codeit.side.lightening.domain.LighteningCondition;
import com.codeit.side.lightening.domain.LighteningOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningEntity.lighteningEntity;

@Repository
@RequiredArgsConstructor
public class LighteningQueryEntityRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<LighteningEntity> findAllBy(LighteningCondition lighteningCondition) {
        BooleanBuilder booleanBuilder = createQueryBuilder(lighteningCondition);

        return jpaQueryFactory.selectFrom(lighteningEntity)
                .where(booleanBuilder)
                .orderBy(createOrder(lighteningCondition.getLighteningOrder()))
                .offset(lighteningCondition.getOffset())
                .limit(lighteningCondition.getLighteningPaging().getSize())
                .fetch();
    }

    private OrderSpecifier<LocalDateTime> createOrder(String order) {
        LighteningOrder lighteningOrder = LighteningOrder.from(order);
        return switch (lighteningOrder) {
            case CREATED_DESC -> lighteningEntity.createdAt.desc();
            case TARGET_DATE_DESC -> lighteningEntity.targetAt.desc();
        };
    }

    private BooleanBuilder createQueryBuilder(LighteningCondition lighteningCondition) {
        return switch (lighteningCondition.getConditionType()) {
            case LIST -> listConditionBuilder(lighteningCondition);
            case MY_CREATED -> myCreatedConditionBuilder(lighteningCondition);
        };
    }

    private BooleanBuilder listConditionBuilder(LighteningCondition lighteningCondition) {
        return LighteningQueryBuilder.Builder()
                .addCategoryCondition(lighteningCondition.getCategory())
                .addCityCondition(lighteningCondition.getCity())
                .addTownCondition(lighteningCondition.getTown())
                .addTargetAtCondition(lighteningCondition.getTargetAt())
                .build();
    }

    private BooleanBuilder myCreatedConditionBuilder(LighteningCondition lighteningCondition) {
        return LighteningQueryBuilder.Builder()
                .addCategoryCondition(lighteningCondition.getCategory())
                .addMyCreatedCondition(lighteningCondition.getCreatedBy())
                .build();
    }
}
