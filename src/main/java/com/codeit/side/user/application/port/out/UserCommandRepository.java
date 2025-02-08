package com.codeit.side.user.application.port.out;

import com.codeit.side.user.domain.User;

public interface UserCommandRepository {

    User saveUser(User user);
}
