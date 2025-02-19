package com.codeit.side.lightening.adapter.out.persistence;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningEntity;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningJpaEntityRepository;
import com.codeit.side.lightening.application.port.out.LighteningCommandRepository;
import com.codeit.side.lightening.domain.Lightening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LighteningCommandRepositoryImpl implements LighteningCommandRepository {
    private final LighteningJpaEntityRepository lighteningJpaEntityRepository;

    @Override
    public Lightening save(String email, Lightening lightening) {
        LighteningEntity lighteningEntity = LighteningEntity.from(email, lightening);
        LighteningEntity save = lighteningJpaEntityRepository.save(lighteningEntity);
        return save.toDomain();
    }
}
