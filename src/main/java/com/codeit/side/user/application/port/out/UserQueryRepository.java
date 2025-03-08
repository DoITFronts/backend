package com.codeit.side.user.application.port.out;

import com.codeit.side.user.adapter.out.persistence.entity.UserEntity;
import com.codeit.side.user.domain.User;

import java.util.List;

public interface UserQueryRepository {
    boolean existsByEmail(String email);

    UserEntity getByEmail(String email);

    List<User> getAllByIds(List<Long> ids);

    List<User> findAllByIds(List<Long> userIds);
}
