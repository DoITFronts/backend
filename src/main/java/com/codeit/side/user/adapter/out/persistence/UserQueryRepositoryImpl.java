package com.codeit.side.user.adapter.out.persistence;

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
}
