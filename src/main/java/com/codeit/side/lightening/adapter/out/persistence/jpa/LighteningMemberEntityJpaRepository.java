package com.codeit.side.lightening.adapter.out.persistence.jpa;

import com.codeit.side.lightening.adapter.out.persistence.entity.LighteningMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LighteningMemberEntityJpaRepository extends JpaRepository<LighteningMemberEntity, Long> {
}
