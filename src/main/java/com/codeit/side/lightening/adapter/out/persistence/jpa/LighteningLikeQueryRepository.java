package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningLikeEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningLikeEntity.lighteningLikeEntity;

@Repository
@RequiredArgsConstructor
public class LighteningLikeQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<LighteningLikeEntity> findAllLighteningLikesBy(String email, List<Long> id) {
        return jpaQueryFactory.selectFrom(lighteningLikeEntity)
                .where(
                        lighteningLikeEntity.email.eq(email),
                        lighteningLikeEntity.lighteningId.in(id),
                        lighteningLikeEntity.isDeleted.eq(false)
                )
                .orderBy(lighteningLikeEntity.createdAt.desc())
                .fetch();
    }
}
