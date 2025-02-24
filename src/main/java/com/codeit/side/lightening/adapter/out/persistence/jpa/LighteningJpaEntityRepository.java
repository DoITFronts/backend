package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LighteningJpaEntityRepository extends JpaRepository<LighteningEntity, Long> {
}
