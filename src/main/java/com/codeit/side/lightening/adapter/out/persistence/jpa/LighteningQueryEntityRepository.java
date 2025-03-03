package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningEntity;
import com.codeit.side.lightening.domain.LighteningCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
                .orderBy(lighteningEntity.createdAt.desc())
                .offset(lighteningCondition.getOffset())
                .limit(lighteningCondition.getLighteningPaging().getSize())
                .fetch();
    }

    private BooleanBuilder createQueryBuilder(LighteningCondition lighteningCondition) {
        return LighteningQueryBuilder.Builder()
                .addCategoryCondition(lighteningCondition.getCategory())
                .addCityCondition(lighteningCondition.getCity())
                .addTownCondition(lighteningCondition.getTown())
                .addTargetAtCondition(lighteningCondition.getTargetAt())
                .build();
    }
}
