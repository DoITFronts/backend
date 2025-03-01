package com.codeit.side.lightening.adapter.out.persistence;

import com.codeit.side.common.adapter.exception.LighteningNotFoundException;
import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningEntity;
import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningLikeEntity;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningJpaEntityRepository;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningLikeQueryRepository;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningMemberEntityQueryRepository;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningQueryEntityRepository;
import com.codeit.side.lightening.application.port.out.LighteningReadRepository;
import com.codeit.side.lightening.domain.Lightening;
import com.codeit.side.lightening.domain.LighteningCondition;
import com.codeit.side.lightening.domain.LighteningLike;
import com.codeit.side.lightening.domain.LighteningMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LighteningReadRepositoryImpl implements LighteningReadRepository {
    private final LighteningJpaEntityRepository lighteningJpaEntityRepository;
    private final LighteningQueryEntityRepository lighteningQueryEntityRepository;
    private final LighteningMemberEntityQueryRepository lighteningMemberEntityQueryRepository;
    private final LighteningLikeQueryRepository lighteningLikeQueryRepository;

    @Override
    public Lightening getById(Long lighteningId) {
        return lighteningJpaEntityRepository.findById(lighteningId)
                .orElseThrow(() -> new LighteningNotFoundException(lighteningId))
                .toDomain();
    }

    @Override
    public boolean isJoined(String email, Long lighteningId) {
        return lighteningMemberEntityQueryRepository.isJoined(email, lighteningId);
    }

    @Override
    public int countByLighteningId(Long id) {
        return lighteningMemberEntityQueryRepository.countByLighteningId(id);
    }

    @Override
    public List<LighteningMember> findAllMembersBy(Long id) {
        return lighteningMemberEntityQueryRepository.findAllLighteningMemberDtosBy(id)
                .stream()
                .map(LighteningMember::from)
                .toList();
    }

    @Override
    public boolean findLighteningLikeBy(String email, Long id) {
        return lighteningMemberEntityQueryRepository.findLighteningLikeBy(email, id);
    }

    @Override
    public List<Lightening> findAllBy(LighteningCondition lighteningCondition) {
        return lighteningQueryEntityRepository.findAllBy(lighteningCondition)
                .stream()
                .map(LighteningEntity::toDomain)
                .toList();
    }

    @Override
    public List<LighteningMember> findAllMembersBy(List<Long> ids) {
        return lighteningMemberEntityQueryRepository.findAllLighteningMemberDtosBy(ids)
                .stream()
                .map(LighteningMember::from)
                .toList();
    }

    @Override
    public List<LighteningLike> findLighteningLikesBy(String email, List<Long> lighteningIds) {
        return lighteningLikeQueryRepository.findAllLighteningLikesBy(email, lighteningIds)
                .stream()
                .map(LighteningLikeEntity::toDomain)
                .toList();
    }
}
