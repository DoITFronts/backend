package com.codeit.side.user.adapter.out.persistence;

import com.codeit.side.common.adapter.exception.UserNotFoundException;
import com.codeit.side.user.adapter.out.persistence.entity.UserEntity;
import com.codeit.side.user.adapter.out.persistence.jpa.UserJpaRepository;
import com.codeit.side.user.application.port.out.UserQueryRepository;
import com.codeit.side.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public UserEntity getByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> findByIds(List<Long> ids) {
        List<UserEntity> userEntities = userJpaRepository.findAllById(ids);
        if (ids.size() != userEntities.size()) {
            List<Long> missingIds = extractMissingIds(ids, userEntities);
            throw new UserNotFoundException(missingIds);
        }
        return userEntities.stream()
                .map(UserEntity::toDomain)
                .toList();
    }

    private List<Long> extractMissingIds(List<Long> ids, List<UserEntity> userEntities) {
        return ids.stream()
                .filter(id -> userEntities.stream()
                        .noneMatch(userEntity -> userEntity.getId().equals(id)))
                .toList();
    }
}
