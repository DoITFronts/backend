package com.codeit.side.user.adapter.out.persistence.jpa;

import com.codeit.side.user.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
}
