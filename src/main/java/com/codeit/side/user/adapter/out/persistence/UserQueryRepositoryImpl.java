package com.codeit.side.user.adapter.out.persistence;

import com.codeit.side.common.adapter.exception.UserNotFoundException;
import com.codeit.side.user.adapter.out.persistence.entity.UserEntity;
import com.codeit.side.user.adapter.out.persistence.jpa.UserJpaRepository;
import com.codeit.side.user.application.port.out.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsById(email);
    }

    @Override
    public UserEntity getByEmail(String email) {
        return userJpaRepository.findById(email)
                .orElseThrow(UserNotFoundException::new);
    }
}
