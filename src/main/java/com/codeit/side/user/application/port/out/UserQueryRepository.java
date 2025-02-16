package com.codeit.side.user.application.port.out;

import com.codeit.side.user.adapter.out.persistence.entity.UserEntity;

public interface UserQueryRepository {

    boolean existsByEmail(String email);

    UserEntity getByEmail(String email);
}
