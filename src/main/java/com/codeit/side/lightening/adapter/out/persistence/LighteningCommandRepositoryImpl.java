package com.codeit.side.lightening.adapter.out.persistence;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningEntity;
import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningLikeEntity;
import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningMemberEntity;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningJpaEntityRepository;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningLikeJpaEntityRepository;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningMemberEntityJpaRepository;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningMemberEntityQueryRepository;
import com.codeit.side.lightening.application.port.out.LighteningCommandRepository;
import com.codeit.side.lightening.domain.Lightening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class LighteningCommandRepositoryImpl implements LighteningCommandRepository {
    private final LighteningJpaEntityRepository lighteningJpaEntityRepository;
    private final LighteningLikeJpaEntityRepository lighteningLikeJpaEntityRepository;
    private final LighteningMemberEntityQueryRepository lighteningMemberEntityQueryRepository;
    private final LighteningMemberEntityJpaRepository lighteningMemberEntityJpaRepository;

    @Override
    public Lightening save(String email, Lightening lightening) {
        LighteningEntity lighteningEntity = LighteningEntity.from(email, lightening);
        LighteningEntity save = lighteningJpaEntityRepository.save(lighteningEntity);
        return save.toDomain();
    }

    @Override
    public void like(String email, Long lighteningId) {
        lighteningLikeJpaEntityRepository.findByLighteningIdAndEmail(lighteningId, email)
                .ifPresentOrElse(
                        LighteningLikeEntity::update,
                        () -> lighteningLikeJpaEntityRepository.save(LighteningLikeEntity.of(lighteningId, email))
                );
    }

    @Override
    public void join(String email, Long id) {
        lighteningMemberEntityQueryRepository.findByIdAndEmail(email, id)
                .ifPresentOrElse(
                        LighteningMemberEntity::update,
                        () -> lighteningMemberEntityJpaRepository.save(LighteningMemberEntity.of(id, email))
                );
    }

    @Override
    public void leave(String email, Long id) {
        lighteningMemberEntityQueryRepository.findByIdAndEmail(email, id)
                .ifPresent(LighteningMemberEntity::delete);
    }

    @Override
    public void update(Long id, String description) {
        lighteningJpaEntityRepository.findById(id)
                .ifPresent(lighteningEntity -> lighteningEntity.update(description));
    }

    @Override
    public void delete(Long id) {
        lighteningJpaEntityRepository.findById(id)
                .ifPresent(LighteningEntity::delete);
    }

    @Override
    public void likesAll(String email, Set<Long> lighteningIds) {
        lighteningIds.forEach(lighteningId -> {
            likeLightening(email, lighteningId);
        });
    }

    private void likeLightening(String email, Long lighteningId) {
        lighteningLikeJpaEntityRepository.findByLighteningIdAndEmail(lighteningId, email)
                .ifPresentOrElse(
                        LighteningLikeEntity::like,
                        () -> lighteningLikeJpaEntityRepository.save(LighteningLikeEntity.of(lighteningId, email))
                );
    }
}
