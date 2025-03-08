package com.codeit.side.user.application.port.in;

import com.codeit.side.user.domain.User;
import com.codeit.side.user.domain.command.UserCommand;

public interface UserUseCase {
    User create(UserCommand user);

    User getUser(String email);
}
