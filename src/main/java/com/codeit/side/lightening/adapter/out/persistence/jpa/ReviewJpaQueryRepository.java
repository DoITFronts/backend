package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.entity.ReviewEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.codeit.side.lightening.adapter.out.persistence.entity.QReviewEntity.reviewEntity;

@Repository
@RequiredArgsConstructor
public class ReviewJpaQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ReviewEntity> findAllByLighteningId(Long lightening, Integer page, Integer size) {
        return queryFactory.selectFrom(reviewEntity)
                .where(reviewEntity.lighteningId.eq(lightening))
                .orderBy(reviewEntity.createdAt.desc())
                .offset((long) (page - 1) * size)
                .limit(size)
                .fetch();
    }

    public int getTotalCountByLighteningId(Long lighteningId) {
        return queryFactory.selectFrom(reviewEntity)
                .where(reviewEntity.lighteningId.eq(lighteningId))
                .fetch()
                .size();
    }
}
