package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LighteningLikeJpaEntityRepository extends JpaRepository<LighteningLikeEntity, Long> {
    Optional<LighteningLikeEntity> findByIdAndEmail(Long id, String email);
}
