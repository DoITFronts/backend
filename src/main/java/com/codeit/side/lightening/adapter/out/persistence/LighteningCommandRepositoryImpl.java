package com.codeit.side.lightening.adapter.out.persistence;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningEntity;
import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningLikeEntity;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningJpaEntityRepository;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningLikeJpaEntityRepository;
import com.codeit.side.lightening.application.port.out.LighteningCommandRepository;
import com.codeit.side.lightening.domain.Lightening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LighteningCommandRepositoryImpl implements LighteningCommandRepository {
    private final LighteningJpaEntityRepository lighteningJpaEntityRepository;
    private final LighteningLikeJpaEntityRepository lighteningLikeJpaEntityRepository;

    @Override
    public Lightening save(String email, Lightening lightening) {
        LighteningEntity lighteningEntity = LighteningEntity.from(email, lightening);
        LighteningEntity save = lighteningJpaEntityRepository.save(lighteningEntity);
        return save.toDomain();
    }

    @Override
    public void like(String email, Long lighteningId) {
        Optional<LighteningLikeEntity> lighteningLikeEntity = lighteningLikeJpaEntityRepository.findByIdAndEmail(lighteningId, email);
        if (lighteningLikeEntity.isPresent()) {
            lighteningLikeEntity.get().update();
            return;
        }
        lighteningLikeJpaEntityRepository.save(LighteningLikeEntity.of(lighteningId, email));
    }
}
