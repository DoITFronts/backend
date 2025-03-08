package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.LighteningDto;
import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningEntity;
import com.codeit.side.lightening.domain.LighteningCondition;
import com.codeit.side.lightening.domain.LighteningOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningEntity.lighteningEntity;
import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningMemberEntity.lighteningMemberEntity;

@Repository
@RequiredArgsConstructor
public class LighteningQueryEntityRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<LighteningEntity> findAllBy(LighteningCondition lighteningCondition) {
        BooleanBuilder booleanBuilder = createQueryBuilder(lighteningCondition);

        if ("END_DESC".equals(lighteningCondition.getLighteningOrder())) {
            booleanBuilder.and(lighteningEntity.endAt.after(LocalDateTime.now()));
        }
        if ("PARTICIPANT_COUNT".equals(lighteningCondition.getLighteningOrder())) {
            return participantDescQuery(lighteningCondition, booleanBuilder);
        }

        return jpaQueryFactory.selectFrom(lighteningEntity)
                .where(booleanBuilder)
                .orderBy(createOrder(lighteningCondition.getLighteningOrder()))
                .offset(lighteningCondition.getOffset())
                .limit(lighteningCondition.getLighteningPaging().getSize())
                .fetch();
    }

    private OrderSpecifier<?> createOrder(String order) {
        LighteningOrder lighteningOrder = LighteningOrder.from(order);
        return switch (lighteningOrder) {
            case CREATED_DESC -> lighteningEntity.createdAt.desc();
            case TARGET_DATE_DESC -> lighteningEntity.targetAt.desc();
            case END_DESC -> lighteningEntity.endAt.desc();
            default -> lighteningEntity.createdAt.desc();
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
                .addIsInactiveCondition(false)
                .addCategoryCondition(lighteningCondition.getCategory())
                .addCityCondition(lighteningCondition.getCity())
                .addTownCondition(lighteningCondition.getTown())
                .addTargetAtCondition(lighteningCondition.getTargetAt())
                .build();
    }

    private BooleanBuilder myCreatedConditionBuilder(LighteningCondition lighteningCondition) {
        return LighteningQueryBuilder.Builder()
                .addIsInactiveCondition(false)
                .addCategoryCondition(lighteningCondition.getCategory())
                .addMyCreatedCondition(lighteningCondition.getCreatedBy())
                .build();
    }

    private List<LighteningEntity> participantDescQuery(LighteningCondition lighteningCondition, BooleanBuilder booleanBuilder) {
        return jpaQueryFactory.select(
                        Projections.constructor(LighteningDto.class,
                                lighteningEntity.id,
                                lighteningEntity.title,
                                lighteningEntity.summary,
                                lighteningEntity.description,
                                lighteningEntity.address,
                                lighteningEntity.city,
                                lighteningEntity.town,
                                lighteningEntity.category,
                                lighteningEntity.targetAt,
                                lighteningEntity.endAt,
                                lighteningEntity.capacity,
                                lighteningEntity.hasImage,
                                lighteningEntity.host,
                                lighteningEntity.minCapacity,
                                lighteningEntity.placeName,
                                lighteningEntity.latitude,
                                lighteningEntity.longitude,
                                lighteningMemberEntity.id.count()
                        )
                )
                .from(lighteningEntity)
                .leftJoin(lighteningMemberEntity)
                .on(lighteningEntity.id.eq(lighteningMemberEntity.lighteningId)
                        .and(lighteningMemberEntity.isDeleted.eq(false)))
                .where(booleanBuilder)
                .groupBy(lighteningEntity.id)
                .orderBy(lighteningMemberEntity.id.count().desc())
                .offset(lighteningCondition.getOffset())
                .limit(lighteningCondition.getLighteningPaging().getSize())
                .fetch()
                .stream()
                .map(LighteningDto::toEntity)
                .toList();
    }
}
