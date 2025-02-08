package com.codeit.side.user.application.port.out;

public interface UserQueryRepository {

    boolean existsByEmail(String email);
}
