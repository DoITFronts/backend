package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningLikeEntity;
import com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningLikeEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LighteningLikeQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<LighteningLikeEntity> findByIdAndEmail(Long lighteningId, String email) {
        LighteningLikeEntity lighteningLikeEntity = jpaQueryFactory.selectFrom(QLighteningLikeEntity.lighteningLikeEntity)
                .where(
                        QLighteningLikeEntity.lighteningLikeEntity.lighteningId.eq(lighteningId),
                        QLighteningLikeEntity.lighteningLikeEntity.email.eq(email)
                )
                .fetchFirst();
        return Optional.ofNullable(lighteningLikeEntity);
    }
}
