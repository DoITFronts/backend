package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.entity.ReviewEntity;
import com.codeit.side.lightening.domain.Category;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningEntity.lighteningEntity;
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

    public boolean existsByLighteningIdAndUserId(Long lighteningId, Long userId) {
        return queryFactory.selectOne()
                .from(reviewEntity)
                .where(
                        reviewEntity.lighteningId.eq(lighteningId),
                        reviewEntity.userId.eq(userId)
                )
                .fetchFirst() != null;
    }

    public List<ReviewEntity> findAllBy(Long userId, String category, Integer size, Integer page) {
        return queryFactory.selectFrom(reviewEntity)
                .innerJoin(lighteningEntity)
                .on(reviewEntity.lighteningId.eq(lighteningEntity.id), lighteningEntity.category.eq(Category.from(category)), lighteningEntity.isInactive.eq(false))
                .where(reviewEntity.userId.eq(userId))
                .orderBy(reviewEntity.createdAt.desc())
                .offset((long) (page - 1) * size)
                .limit(size)
                .fetch();
    }

    public int countAllBy(Long userId, String category) {
        return queryFactory.selectFrom(reviewEntity)
                .innerJoin(lighteningEntity)
                .on(reviewEntity.lighteningId.eq(lighteningEntity.id), lighteningEntity.category.eq(Category.from(category)), lighteningEntity.isInactive.eq(false))
                .where(reviewEntity.userId.eq(userId))
                .fetch()
                .size();
    }
}
