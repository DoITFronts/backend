package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.LighteningMemberDto;
import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningMemberEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningLikeEntity.lighteningLikeEntity;
import static com.codeit.side.lightening.adapter.out.persistence.entity.QLighteningMemberEntity.lighteningMemberEntity;
import static com.codeit.side.user.adapter.out.persistence.entity.QUserEntity.userEntity;

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

    public List<LighteningMemberDto> findAllLighteningMemberDtosBy(Long id) {
        return jpaQueryFactory.select(Projections.constructor(LighteningMemberDto.class,
                        lighteningMemberEntity.lighteningId,
                        userEntity.id,
                        lighteningMemberEntity.email,
                        userEntity.name)
                )
                .from(lighteningMemberEntity)
                .innerJoin(userEntity)
                .on(lighteningMemberEntity.email.eq(userEntity.email))
                .where(
                        lighteningMemberEntity.lighteningId.eq(id),
                        lighteningMemberEntity.isDeleted.eq(false)
                )
                .fetch();
    }

    public boolean findLighteningLikeBy(String email, Long id) {
        if (email == null || email.isBlank()) {
            return false;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(lighteningLikeEntity.email.eq(email))
                .and(lighteningLikeEntity.lighteningId.eq(id))
                .and(lighteningLikeEntity.isDeleted.eq(false));

        return jpaQueryFactory.selectOne()
                .from(lighteningLikeEntity)
                .where(booleanBuilder)
                .fetchOne() != null;
    }

    public List<LighteningMemberDto> findAllLighteningMemberDtosBy(List<Long> ids) {
        return jpaQueryFactory.select(Projections.constructor(LighteningMemberDto.class,
                        lighteningMemberEntity.lighteningId,
                        userEntity.id,
                        lighteningMemberEntity.email,
                        userEntity.name)
                )
                .from(lighteningMemberEntity)
                .innerJoin(userEntity)
                .on(lighteningMemberEntity.email.eq(userEntity.email))
                .where(
                        lighteningMemberEntity.lighteningId.in(ids),
                        lighteningMemberEntity.isDeleted.eq(false)
                )
                .fetch();
    }
}
