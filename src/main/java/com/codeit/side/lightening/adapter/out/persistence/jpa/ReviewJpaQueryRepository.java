package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.ReviewDto;
import com.codeit.side.lightening.adapter.out.persistence.entity.ReviewEntity;
import com.codeit.side.lightening.domain.Category;
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
import static com.codeit.side.lightening.adapter.out.persistence.entity.QReviewEntity.reviewEntity;

@Repository
@RequiredArgsConstructor
public class ReviewJpaQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<ReviewEntity> findAllByLighteningId(Long lightening, Integer page, Integer size) {
        return jpaQueryFactory.selectFrom(reviewEntity)
                .where(reviewEntity.lighteningId.eq(lightening))
                .orderBy(reviewEntity.createdAt.desc())
                .offset((long) (page - 1) * size)
                .limit(size)
                .fetch();
    }

    public int getTotalCountByLighteningId(Long lighteningId) {
        return jpaQueryFactory.selectFrom(reviewEntity)
                .where(reviewEntity.lighteningId.eq(lighteningId))
                .fetch()
                .size();
    }

    public boolean existsByLighteningIdAndUserId(Long lighteningId, Long userId) {
        return jpaQueryFactory.selectOne()
                .from(reviewEntity)
                .where(
                        reviewEntity.lighteningId.eq(lighteningId),
                        reviewEntity.userId.eq(userId)
                )
                .fetchFirst() != null;
    }

    public List<ReviewEntity> findAllBy(Long userId, String category, Integer size, Integer page) {
        return jpaQueryFactory.selectFrom(reviewEntity)
                .innerJoin(lighteningEntity)
                .on(reviewEntity.lighteningId.eq(lighteningEntity.id), lighteningEntity.category.eq(Category.from(category)), lighteningEntity.isInactive.eq(false))
                .where(reviewEntity.userId.eq(userId))
                .orderBy(reviewEntity.createdAt.desc())
                .offset((long) (page - 1) * size)
                .limit(size)
                .fetch();
    }

    public int countAllBy(Long userId, String category) {
        return jpaQueryFactory.selectFrom(reviewEntity)
                .innerJoin(lighteningEntity)
                .on(reviewEntity.lighteningId.eq(lighteningEntity.id), lighteningEntity.category.eq(Category.from(category)), lighteningEntity.isInactive.eq(false))
                .where(reviewEntity.userId.eq(userId))
                .fetch()
                .size();
    }

    public List<ReviewDto> findAllBy(String category, String city, String town, LocalDateTime targetAt, String order, Integer size, Integer page) {
        BooleanBuilder booleanBuilder = createReviewBooleanBuilder(category, city, town, targetAt);

        if ("PARTICIPANT_COUNT".equals(order)) {
            return jpaQueryFactory.select(
                            Projections.constructor(ReviewDto.class,
                                    reviewEntity.id,
                                    reviewEntity.score,
                                    reviewEntity.content,
                                    reviewEntity.lighteningId,
                                    reviewEntity.userId,
                                    reviewEntity.createdAt,
                                    lighteningMemberEntity.id.count()
                            )
                    )
                    .from(reviewEntity)
                    .innerJoin(lighteningEntity)
                    .on(reviewEntity.lighteningId.eq(lighteningEntity.id))
                    .leftJoin(lighteningMemberEntity)
                    .on(lighteningEntity.id.eq(lighteningMemberEntity.lighteningId)
                            .and(lighteningMemberEntity.isDeleted.eq(false)))
                    .where(booleanBuilder)
                    .groupBy(reviewEntity.id, reviewEntity.score, reviewEntity.content,
                            reviewEntity.lighteningId, reviewEntity.userId, reviewEntity.createdAt)
                    .orderBy(lighteningMemberEntity.id.count().desc())
                    .offset((long) (page - 1) * size)
                    .limit(size)
                    .fetch();
        }

        return jpaQueryFactory.select(Projections.constructor(ReviewDto.class,
                        reviewEntity.id,
                        reviewEntity.score,
                        reviewEntity.content,
                        reviewEntity.lighteningId,
                        reviewEntity.userId,
                        reviewEntity.createdAt,
                        reviewEntity.userId
                ))
                .from(reviewEntity)
                .innerJoin(lighteningEntity)
                .on(reviewEntity.lighteningId.eq(lighteningEntity.id))
                .where(booleanBuilder)
                .orderBy(createOrder(order))
                .offset((long) (page - 1) * size)
                .limit(size)
                .fetch();
    }

    public int countAllBy(String category, String city, String town, LocalDateTime targetAt, String order) {
        BooleanBuilder booleanBuilder = createReviewBooleanBuilder(category, city, town, targetAt);

        if ("PARTICIPANT_COUNT".equals(order)) {
            return jpaQueryFactory.select(
                            Projections.constructor(ReviewDto.class,
                                    reviewEntity.id,
                                    reviewEntity.score,
                                    reviewEntity.content,
                                    reviewEntity.lighteningId,
                                    reviewEntity.userId,
                                    reviewEntity.createdAt,
                                    lighteningMemberEntity.id.count()
                            )
                    )
                    .from(reviewEntity)
                    .innerJoin(lighteningEntity)
                    .on(reviewEntity.lighteningId.eq(lighteningEntity.id))
                    .leftJoin(lighteningMemberEntity)
                    .on(lighteningEntity.id.eq(lighteningMemberEntity.lighteningId)
                            .and(lighteningMemberEntity.isDeleted.eq(false)))
                    .where(booleanBuilder)
                    .groupBy(reviewEntity.id, reviewEntity.score, reviewEntity.content,
                            reviewEntity.lighteningId, reviewEntity.userId, reviewEntity.createdAt)
                    .fetch()
                    .size();
        }
        return jpaQueryFactory.select(Projections.constructor(ReviewDto.class,
                        reviewEntity.id,
                        reviewEntity.score,
                        reviewEntity.content,
                        reviewEntity.lighteningId,
                        reviewEntity.userId,
                        reviewEntity.createdAt,
                        reviewEntity.userId
                ))
                .from(reviewEntity)
                .innerJoin(lighteningEntity)
                .on(reviewEntity.lighteningId.eq(lighteningEntity.id))
                .where(booleanBuilder)
                .fetch()
                .size();
    }

    private OrderSpecifier<?> createOrder(String order) {
        return switch (LighteningOrder.from(order)) {
            case CREATED_DESC -> reviewEntity.createdAt.desc();
            case RATING_DESC -> reviewEntity.score.desc();
            default -> reviewEntity.createdAt.asc();
        };
    }

    private BooleanBuilder createReviewBooleanBuilder(String category, String city, String town, LocalDateTime targetAt) {
        return ReviewBooleanBuilder.Builder()
                .addIsInactiveCondition(false)
                .addCategoryCondition(category)
                .addCityCondition(city)
                .addTownCondition(town)
                .addTargetAtCondition(targetAt)
                .build();
    }
}
