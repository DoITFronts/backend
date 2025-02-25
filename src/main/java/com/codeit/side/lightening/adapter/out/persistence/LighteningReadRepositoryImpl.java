package com.codeit.side.lightening.adapter.out.persistence;

import com.codeit.side.common.adapter.exception.LighteningNotFoundException;
import com.codeit.side.lightening.adapter.out.persistence.jpa.LighteningJpaEntityRepository;
import com.codeit.side.lightening.application.port.out.LighteningReadRepository;
import com.codeit.side.lightening.domain.Lightening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LighteningReadRepositoryImpl implements LighteningReadRepository {
    private final LighteningJpaEntityRepository lighteningJpaEntityRepository;

    @Override
    public Lightening getById(Long lighteningId) {
        return lighteningJpaEntityRepository.findById(lighteningId)
                .orElseThrow(() -> new LighteningNotFoundException(lighteningId))
                .toDomain();
    }
}
