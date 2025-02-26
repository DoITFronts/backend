package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningMemberEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningMemberEntity.lighteningMemberEntity;

@Repository
@RequiredArgsConstructor
public class LighteningMemberEntityQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public boolean isJoined(String email, Long lighteningId) {
        return jpaQueryFactory.select(lighteningMemberEntity.id)
                .from(lighteningMemberEntity)
                .where(
                        lighteningMemberEntity.email.eq(email),
                        lighteningMemberEntity.lighteningId.eq(lighteningId),
                        lighteningMemberEntity.isDeleted.eq(false)
                )
                .fetchFirst() != null;
    }

    public Optional<LighteningMemberEntity> findByIdAndEmail(String email, Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(lighteningMemberEntity)
                .where(
                        lighteningMemberEntity.email.eq(email),
                        lighteningMemberEntity.lighteningId.eq(id),
                        lighteningMemberEntity.isDeleted.eq(false)
                )
                .fetchFirst());
    }

    public int countByLighteningId(Long id) {
        return jpaQueryFactory.selectOne()
                .from(lighteningMemberEntity)
                .where(
                        lighteningMemberEntity.lighteningId.eq(id),
                        lighteningMemberEntity.isDeleted.eq(false)
                )
                .fetch()
                .size();
    }
}
