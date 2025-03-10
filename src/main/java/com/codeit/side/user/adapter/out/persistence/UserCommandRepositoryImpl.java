package com.codeit.side.user.adapter.out.persistence;

import com.codeit.side.common.adapter.exception.UserNotFoundException;
import com.codeit.side.user.adapter.out.persistence.entity.UserEntity;
import com.codeit.side.user.adapter.out.persistence.jpa.UserJpaRepository;
import com.codeit.side.user.application.port.out.UserCommandRepository;
import com.codeit.side.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCommandRepositoryImpl implements UserCommandRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User saveUser(User user) {
        return userJpaRepository.save(UserEntity.from(user))
                .toDomain();
    }

    @Override
    public User updateUser(String email, String description, String nickname, boolean hasImage) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new)
                .update(description, nickname, hasImage)
                .toDomain();
    }

}
